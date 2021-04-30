
# dashwood-calendar
Custom calendar for Persian and English calendars

Screenshot 1 | Screenshot 2
------------ | -------------
![Screenshot 1](/images/persian_calendar.gif) | ![Screenshot 2](/images/gregorian_calendar.gif)

# How it work

Add this two items in gradle

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	
  
  dependencies {
		   implementation 'com.github.dashwood01:Calendar:1.0.2'
	}
  ```

### And you can now use it

Add **ViewPager2** in layout

```xml
 <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

#### And then init in activity

```java
DashwoodCalendar dashwoodCalendar = new DashwoodCalendar(this,
                    viewPager, 1300, 1500, DashwoodCalendar.PERSIAN_LANGUAGE);
            dashwoodCalendar.setOnClickCalendarListener((day, month, year, monthName, dayOfWeek, dayOfWeekNumber, fullDateWithMonthString, fullDate, gregorianDate) -> {
                Toast.makeText(this, "Day : " + day + "\nMonth : " + month + "\nyear : " + year + "\nmonthName : " + monthName + "\nday of week : " + dayOfWeek + "\nday of week number : " + dayOfWeekNumber +
                        "\nfull date with month string : " + fullDateWithMonthString + "\nfull date : " + fullDate + "\ngregorian date : " + gregorianDate, Toast.LENGTH_LONG).show();
            });
	    //You must call init after all changes
            dashwoodCalendar.init();
```

### If you want disable some days just set information

```java
ArrayList<InformationDisableDay> informationDisableDays = new ArrayList<>();
	//The date must be Gregorian
        String[] dates = {"2021-05-17","2021-05-18","2021-05-19"};
        for (String date : dates){
            InformationDisableDay informationDisableDay = new InformationDisableDay();
            informationDisableDay.setDate(date);
            informationDisableDays.add(informationDisableDay);
        }
        dashwoodCalendar.setInformationDisableDays(informationDisableDays);
	dashwoodCalendar.init();
```

### If you want it to always be the maxYear of the current year

```java
// DashwoodCalendar.THIS_YEAR_GREGORIAN
// DashwoodCalendar.THIS_YEAR_PRESIAN

DashwoodCalendar dashwoodCalendar = new DashwoodCalendar(this,
                    viewPager, 1300, DashwoodCalendar.THIS_YEAR_PERSIAN, DashwoodCalendar.PERSIAN_LANGUAGE);
		 dashwoodCalendar.init();
```



# Very important

Always remember that the **init()** function must be the last function called.

You can see simple code [here](https://github.com/dashwood01/Calendar/tree/master/app/src/main/java/com/dashwood/calendar)





## And you can also use this items

Options | What work
------------ | -------------
setBackgroundNowDay | Change the background of the now day button
setBackgroundWeekEndDay | Change the background of the weekend day button
setBackgroundEnableDay | Change the background of the enable day button
setBackgroundDisableDay | Change the background of the disable day button
setBackgroundWeekName | Change the background of the week name on top calendar
setTextWeekNameColor | Change the text color of the week name on top calendar
setTextWeekEndColor | Change the text color of the week end day button
setTextEnableDayColor | Change the text color of the enable day button
setTextNowColor | Change the text color of the now day button
setTextDisableDayColor | Change the text color of the disable day button
setBackgroundBtnYear | Change the background of the year button on top calendar
setBackgroundBtnToday | change the background of the today button
setBackgroundBtnMonth | Change the background of the month button on top calendar
setBackgroundColorRootOptionCalendar | Change the background of the buttons on the top of the calendar
setTextColorBtnToday | Change the color of text today button
setTextColorBtnYear | Change the color of text year button
setTextColorBtnMonth | Change the color text month button
setInformationDisableDays | You can disable some days you want
setTextColorMonthAndYear | Change the color of the month and year list
setTextSizeMonthAndYear | Change the text size of the month and year list
setTextSizeDay | Change the text size of the days calendar
setTextSizeWeekName | Change the text size of the week name top of calendar
setTextSizeWeekEnd | Change the text size of the week end button
setTextSizeNowDay | Change the text size of the now day button
setBackgroundMonthAndYear | Change the background of the month and year list
setRadius | Change the radius of the calendar days
setRadiusMonthAndYear | Change the radius of the month and year list
setDayWidth | Change the width of all days
setDayHeight | Change the height of all days
setDisableWeekEnd | All week end clicables - [x] true - [x] false
setOnClickCalendarListener | Calendar listener
