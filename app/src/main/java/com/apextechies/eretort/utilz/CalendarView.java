package com.apextechies.eretort.utilz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apextechies.eretort.R;
import com.apextechies.eretort.model.YearHolidayModel;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Shankar on 1/13/2018.
 */

public class CalendarView extends LinearLayout
{
    // for logging
    private static final String LOGTAG = "Calendar View";

    // how many days to show, defaults to six weeks, 42 days
    private static final int DAYS_COUNT = 42;

    // default date format
    private static final String DATE_FORMAT = "MMM yyyy";

    // date format
    private String dateFormat;

    // current displayed month
    private Calendar currentDate = Calendar.getInstance();

    //event handling
    private EventHandler eventHandler = null;

    private ArrayList<YearHolidayModel> yearHolidayModels = new ArrayList<>();

    // internal components
    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;

    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    // month-season association (northern hemisphere, sorry australia :)
    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CalendarView(Context context)
    {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    /**
     * Load control xml layout
     */
    private void initControl(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs)
    {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try
        {
            // try to load provided date format, and fallback to default otherwise
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        }
        finally
        {
            ta.recycle();
        }
    }
    private void assignUiElements()
    {
        // layout is inflated, assign local variables to components
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
    }

    private void assignClickHandlers()
    {
        // add one month and refresh UI
        btnNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        // subtract one month and refresh UI
        btnPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        // long-pressing a day
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id)
            {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    eventHandler.onDayLongPress((Date) adapterView.getItemAtPosition(i));
                }
                catch (NullPointerException e)
                {

                }
            }
        });
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar()
    {
        updateCalendar(null,yearHolidayModels);
    }

    /**
     * Display dates correctly in grid
     */
    public void updateCalendar(HashSet<Date> events, ArrayList<YearHolidayModel> yearHolidayModels )
    {
        try {
            this.yearHolidayModels = yearHolidayModels;
        }
        catch (NullPointerException e)
        {

        }
        catch (Exception e)
        {

        }
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
    }


    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        // days with events
        private HashSet<Date> eventDays;

        // for view inflation
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
        {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);

            // if this day has an event, specify event image
            view.setBackgroundResource(0);
            TextView dateText = (TextView)view.findViewById(R.id.dateText);
            //	ImageView image = (ImageView) view.findViewById(R.id.image);
            if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
						/*view.setBackgroundResource(R.drawable.reminder);*/
                        break;
                    }
                }
            }

            // clear styling
            dateText.setTypeface(null, Typeface.NORMAL);
            dateText.setTextColor(Color.BLACK);

            Log.e("Month : Today's Month", ""+month+" : "+today.getMonth()) ;
            Log.e("Year : Today year", ""+year+" : "+today.getYear()) ;
            if (month < today.getMonth() && year <= today.getYear())
            {
                // if this day is outside current month, grey it out
                //dateText.setTextColor(getResources().getColor(R.color.greyed_out));
            }
            //else if (trendingPostModels.contains(""+(1900+today.getYear())));
            else if (day == today.getDate())
            {
                dateText.setTypeface(null, Typeface.BOLD);
                dateText.setTextColor(getResources().getColor(R.color.today));
                //image.setVisibility(VISIBLE);
            }
            if (yearHolidayModels.size()>0) {
                if (day<=9) day = Integer.parseInt(String.format("%0"+day+"d", day));
                String cal_date = "" + day + "/" + "" + (month+1) + "/" + "" + (year + 1900);
                Log.e("dddddddddddddddddddddd",cal_date);
                for (int i=0; i<yearHolidayModels.size(); i++) {
                    if (yearHolidayModels.get(i).getDate().equals(cal_date)) {
                        // if it is today, set it to blue/bold
                        if (!yearHolidayModels.get(i).getName().equals("null"))
                        {
                            createViewAndShow(view,yearHolidayModels.get(i).getImage());
                        }

                    }
                }
            }

            // set text
            dateText.setText(String.valueOf(date.getDate()));

            return view;
        }

        private void createViewAndShow(View view, String profile_image) {
            CircularImageView image;
            RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.ralativeImage);

            image = new CircularImageView(getContext());
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(100,100));
            //image.setMaxHeight(20);
            //image.setMaxWidth(20);
            image.setForegroundGravity(Gravity.CENTER);
           // Glide.with(getContext()).load(profile_image).asBitmap().into(image);

            // Adds the view to the layout
            layout.addView(image);

        }
    }



    /**
     * Assign event handler to be passed needed events
     */
    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    /**
     * This interface defines what events to be reported to
     * the outside world
     */
    public interface EventHandler
    {
        void onDayLongPress(Date date);
    }
}