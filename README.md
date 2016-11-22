# Sectioner
An Android RecyclerView.Adapter management library that makes creating and editing heterogenous view based lists easy. 

## Introduction
Setting up a RecyclerView and its adapter in Android is pretty straightforward UNTIL you hit the heterogenous view based lists. Then its just a complex often ugly mathematical mapping of position to view that developers have to create manually for each type of view their list displays. Its grunt work and it slows you down and as a developer you shouldn't have to deal with it. 

What Sectioner does is generalize the aforementioned mapping so that you can focus on what's important and let Sectioner handle changes to your RecyclerView's data. Sectioner offers a consistent API for creating and editing heterogenous view lists in RecyclerViews. Behind the scenes Sectioner constantly keeps track of item and view positions and automatically detects whether an item has been added, edited or removed and animates the changes in your RecyclerView. 

## Core Classes
1. Section - A Section is a grouping of data items which are represented by a single view type. With Sectioner you can add multiple Sections to your RecyclerView, each representing a different view type. Sections can contain a single item (for headers/footers or separators) or multiple items (for traditional list items). You can also extend and override a Section's default behavior and it is encouraged that you do so.
2. SectionManager - The SectionManager is what keeps track of Sections and relays the information to the SectionedRecyclerViewAdapter so that they are rendered on screen. Any change to the underlying Sections with a SectionManager immediately trigger a position mapping update following which the adapter is updated. 
3. SectionedRecyclerViewAdapter - This is an extension of the base adapter class for interfacing with a SectionManager.

These clases have been extensively JavaDocd. It is recommended that you review the source code to better understand how these classes function.

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Sectioner-green.svg?style=true)](https://android-arsenal.com/details/1/4079)
