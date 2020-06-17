package com.ponomarevss.myweatherapp;

public class MainActivity extends BaseActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(bottomMenuListener);
//        if (savedInstanceState == null) {
//            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
//        }
//        setBackgroundView();

//    }

//    private BottomNavigationView.OnNavigationItemSelectedListener bottomMenuListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    HomeFragment mainFragment = HomeFragment.newInstance();
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, mainFragment)
//                            .commit();
//                    return true;
//                case R.id.navigation_place:
//                    PlacesFragment placesFragment = PlacesFragment.newInstance();
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, placesFragment)
//                            .commit();
//                    return true;
//                case R.id.navigation_settings:
//                    SettingsFragment settingsFragment = SettingsFragment.newInstance();
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, settingsFragment)
//                            .commit();
//                    return true;
//            }
//            return false;
//        }
//    };

//    private int getBackgroundIndex() {
//        return getPreferences(MODE_PRIVATE).getInt(INDEX, INIT_INDEX);
//    }
//
//    void setBackgroundView() {
//        ImageView background = findViewById(R.id.background);
//        TypedArray images = getResources().obtainTypedArray(R.array.city_images);
//        if (getBackgroundIndex() == INIT_INDEX) {
//            background.setImageResource(R.drawable.background_default);
//        } else {
//            background.setImageResource(images.getResourceId(getBackgroundIndex(), -1));
//        }
//        background.setVisibility(View.VISIBLE);
//        images.recycle();
//    }

}
