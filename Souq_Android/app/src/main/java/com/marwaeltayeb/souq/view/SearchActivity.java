package com.marwaeltayeb.souq.view;

import static com.marwaeltayeb.souq.utils.Constant.KEYWORD;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;

import com.marwaeltayeb.souq.R;
import com.marwaeltayeb.souq.adapter.WordAdapter;
import com.marwaeltayeb.souq.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String HISTORY_DATA = "history_data";
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_RIGHT = 2;

    private ActivitySearchBinding binding;
    private String word;
    private WordAdapter adapter;
    private List<String> list;
    private SharedPreferences sharedpreferences;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().setElevation(0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        binding.wordList.setOnItemClickListener(this);
        binding.wordList.setOnItemLongClickListener(this);

        // Return a collection view of the values contained in this map
        list = new ArrayList<>(getWords(this).keySet());

        adapter = new WordAdapter(this,  list);
        binding.wordList.setDivider(null);
        binding.wordList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

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

        binding.editQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click
                Intent searchIntent = new Intent(SearchActivity.this, ResultActivity.class);
                word = binding.editQuery.getText().toString().trim();
                // Set Key with its specific key
                setWord(getApplicationContext(), word, word);
                searchIntent.putExtra(KEYWORD, word);
                startActivity(searchIntent);
                return true;
            }
            return false;
        });

        binding.editQuery.setOnTouchListener((v, event) -> {
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
        });

        sharedpreferences.registerOnSharedPreferenceChangeListener(prefChangeListener);
    }

    public void setWord(Context context , String key , String word){
        sharedpreferences = context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(String.valueOf(key), word);
        editor.apply();
    }

    public Map<String, ?> getWords(Context context){
        sharedpreferences = context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE);
        // Returns a map containing a list of pairs key/value representing the preferences.
        return sharedpreferences.getAll();
    }

    public static void clearSharedPreferences(Context context){
        context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE).edit().clear().apply();
    }

    public static void clearOneItemInSharedPreferences(String key, Context context){
        context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE).edit().remove(key).apply();
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(prefChangeListener);
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
        // Send KEYWORD to ResultActivity
        intent.putExtra(KEYWORD, list.get(position));
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v("long clicked","position: " + position);
        // Get value of a specific position
        word = list.get(position);
        // Set word as a key
        clearOneItemInSharedPreferences(word, getApplicationContext());
        // Remove element from adapter
        adapter.remove(word);
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
        return true;
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener prefChangeListener = (sharedPreferences, key) -> {
        if (key.equals(word)) {
            // Clear the adapter, then add list
            adapter.clear();
            list = new ArrayList<>(getWords(getApplicationContext()).keySet());
            adapter.addAll(list);
            binding.wordList.setAdapter(adapter);
        }
    };
}
