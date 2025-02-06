# Product Listing & Adding App

## Overview

This Android application consists of two screens: 
1. **Product Listing Screen**: Displays a list of products, allows searching, and provides navigation to the Add Product screen.
2. **Add Product Screen**: Allows users to add a new product with product type, name, selling price, tax rate, and optional image upload.

The app follows **MVVM architecture**, uses **Jetpack Compose** for UI, and implements **offline functionality** using WorkManager and RoomDB.

## Features

### **Product Listing Screen**
- Fetches and displays a list of products from the API.
- Search functionality for filtering products.
- Displays product images (uses a default image if no URL is provided).
- Shows a progress indicator while loading.
- A button to navigate to the Add Product screen.

### **Add Product Screen**
- Implemented using `BottomSheetDialogFragment`.
- Allows users to enter:
  - **Product Type** (Dropdown selection)
  - **Product Name** (Text input)
  - **Selling Price** (Decimal validation)
  - **Tax Rate** (Decimal validation)
  - **Image Upload** (JPEG/PNG with 1:1 ratio)
- Form validation for required fields.
- Shows progress indicator while submitting data.
- Displays success/failure messages and a notification on completion.
- Posts data using the `POST` method to `https://app.getswipe.in/api/public/add`.

### **Offline Support**
- Products added while offline are stored in RoomDB.
- WorkManager syncs pending products once an internet connection is available.

## Tech Stack

- **Jetpack Compose** - UI Development
- **MVVM Architecture** - For clean code separation
- **Ktor** - Networking and API calls
- **Koin** - Dependency Injection
- **RoomDB** - Local storage for offline functionality
- **WorkManager** - Background task scheduling for syncing data
- **Jetpack Navigation Component** - Handles fragment navigation
- **Coil** - Efficient image loading with Ktor support
- **DataStore** - Lightweight storage for user preferences

## Libraries Used

The project uses the following dependencies:

```toml
[versions]
agp = "8.5.2"
core = "11.1.0"
kotlin = "2.0.21"
coreKtx = "1.15.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
lifecycleRuntimeKtx = "2.8.7"
activityCompose = "1.9.3"
composeBom = "2024.04.01"
ktor = "2.3.12"
coil = "3.0.0-alpha06"
koin = "3.6.0-alpha3"
koinCompose = "1.2.0-Beta4"
datastore = "1.1.1"
navigation = "2.8.3"
