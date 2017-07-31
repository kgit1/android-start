package com.example.a.appgridlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout layout = (GridLayout) findViewById(R.id.gridLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),GridExampleActivity.class);
                startActivity(intent);

            }
        });
    }

    public void click(View view){

        Intent intent = new Intent(getApplicationContext(),GridExampleActivity.class);
        startActivity(intent);

    }
}



/*

Mostly Used:
0000 => android:numColumns=”auto_fit”
0001 => android:verticalSpacing=”5dp”
0010 => android:horizontalSpacing=”10dp”
0011 => android:stretchMode=”columnWidth”
0100 => android:columnWidth=”90dp”
0101 => android:smoothScrollbar=”true”

Rarely Used:
0110 => android:listSelector=”@color/red”
0111 => android:drawSelectorOnTop=”true”
1000 => android:scrollbarStyle=”insideOverlay”
1001 => android:scrollbars=”vertical”
0000. android:numColumns
Used to set number of columns we wants inside the GridView. we can also set “auto_fit” value so that we developer need not to bother about number of columns.

0001. android:verticalSpacing
Used to set spacing between two gridview items vertically.

0010. android:horizontalSpacing
Used to set spacing between two gridview items horizontally.

0011. andorid:stretchMode
Used to control how items are stretched to fill their space.

0100. android:columnWidth
Used to set Width of the Column.
0101. android:smoothScrollbar=”true”
Used to control scrollbar effectively and When set to true, the list will use a more refined calculation method based on the pixels height of the items visible on screen.

0110. android:listSelector=”@color/red”
Used to define the Selector image/color, selector image/color will be appeared whenever we focus/pressed on the GridView item.

0111. android:drawSelectorOnTop=”true”
Used to control the selector image/color and When set to true, the selector will be drawn over the selected item.

1000. android:scrollbarStyle=”insideOverlay”
Used to define the scroll bar position and its style.

1001. android:scrollbars=”vertical”
Used to define scrollbars such as Horizontal, vertical or both.

 */
