package com.aashreys.sectioner.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aashreys.sectioner.SectionManager;
import com.aashreys.sectioner.SectionedRecyclerViewAdapter;
import com.aashreys.sectioner.sample.sections.AlbumsSection;
import com.aashreys.sectioner.sample.sections.FooterSection;
import com.aashreys.sectioner.sample.sections.HeaderSection;
import com.aashreys.sectioner.sample.sections.SeparatorSection;
import com.aashreys.sectioner.sample.sections.SongsSection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview) RecyclerView                 recyclerView;
    private                      SectionedRecyclerViewAdapter adapter;
    private                      SectionManager               sectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        createAndAddSections();
    }

    private void setupRecyclerView() {
        adapter = new SectionedRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void createAndAddSections() {
        // Retrieve section manager from adapter
        sectionManager = adapter.getSectionManager();

        // Create your sections and add data
        HeaderSection headerSection = new HeaderSection();
        SeparatorSection songsSeparatorSection = new SeparatorSection("Top Audiotracks");

        SongsSection songsSection = new SongsSection();
        songsSection.addAll(getResources().getStringArray(R.array.array_songs)); // Adding data

        SeparatorSection albumsSeparatorSection = new SeparatorSection("Top Records");

        AlbumsSection albumsSection = new AlbumsSection();
        albumsSection.addAll(getResources().getStringArray(R.array.array_albums)); // Adding data

        FooterSection footerSection = new FooterSection();

        // Add sections to SectionManager.
        // You can also use SectionManager#add(Section)
        sectionManager.addAll(
                headerSection,
                songsSeparatorSection,
                songsSection,
                albumsSeparatorSection,
                albumsSection,
                footerSection
        );
    }
}
