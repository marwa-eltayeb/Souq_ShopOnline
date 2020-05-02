package com.marwaeltayeb.souq.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.WordAdapter;
import com.marwaeltayeb.souq.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.marwaeltayeb.souq.utils.Constant.KEYWORD;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ActivitySearchBinding binding;
    private String word;
    private WordAdapter adapter;
    private List<String> list;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().setElevation(0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        binding.wordList.setOnItemClickListener(this);

        list = new ArrayList<>(getWords(this).keySet());

        adapter = new WordAdapter(this,  list);
        binding.wordList.setDivider(null);
        binding.wordList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.editQuery.getText().toString().trim().length() == 1) {

                    binding.editQuery.clearFocus();
                    binding.editQuery.requestFocus();
                    binding.editQuery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_back, 0, R.drawable.ic_close, 0);
                }
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        binding.editQuery.requestFocus();

        binding.editQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Your piece of code on keyboard search click
                    Intent searchIntent = new Intent(SearchActivity.this, ResultActivity.class);
                    word = binding.editQuery.getText().toString().trim();
                    setWord(getApplicationContext(), word, word);
                    searchIntent.putExtra(KEYWORD, word);
                    startActivity(searchIntent);
                    return true;
                }
                return false;
            }
        });

        binding.editQuery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    if (event.getRawX() >= (binding.editQuery.getRight() - binding.editQuery.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        binding.editQuery.getText().clear();
                        return true;
                    }else if ((event.getRawX() + binding.editQuery.getPaddingLeft()) <= (binding.editQuery.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + binding.editQuery.getLeft())) {
                        Intent mainIntent = new Intent(SearchActivity.this, ProductActivity.class);
                        startActivity(mainIntent);
                        return true;
                    }
                }
                return false;
            }
        });

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(word)) {
                    adapter.clear();
                    list = new ArrayList<>(getWords(getApplicationContext()).keySet());
                    adapter.addAll(list);
                    binding.wordList.setAdapter(adapter);
                }
            }
        };

        sharedpreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void setWord(Context context , String key , String word){
        sharedpreferences = context.getSharedPreferences("history_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(String.valueOf(key), word);
        editor.apply();
    }

    public Map<String, ?> getWords(Context context){
        sharedpreferences = context.getSharedPreferences("history_data", Context.MODE_PRIVATE);
        return sharedpreferences.getAll();
    }

    public static void clearSharedPreferences(Context context){
        context.getSharedPreferences("history_data", Context.MODE_PRIVATE).edit().clear().apply();
    }


    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(listener);
        super.onDestroy();
    }

    public void clearAll(View view) {
        clearSharedPreferences(getApplicationContext());
        adapter.clear();
        Toast.makeText(SearchActivity.this, "Cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra(KEYWORD, list.get(position));
        startActivity(intent);
    }
}
