# Vehicle Gallery App - Mobile Coding Challenge

## Architecture Overview

The **Vehicle Gallery App** follows modern Android development best practices with **MVVM architecture** and **clean code principles**.

### Key Features of the Architecture:
- **StateFlow/LiveData** for clean state management.
- **Jetpack Compose** for a declarative UI that reacts to state changes.
- **Hilt** for **Dependency Injection** to ensure modularity and testability.
- **Coroutines** for handling background tasks efficiently without blocking the main thread.
- **Separation of Concerns**: Each class and function has a clear and single responsibility, making the app easy to maintain and test.

## Assumptions

1. **Vehicle Gallery Flow**:
    - User selects search criteria to view vehicles on mobile.de app.
    - User clicks on a vehicle to view its details.
    - Vehicle details and images are displayed.
    - User clicks on an image to view the high-resolution version.

2. **Image ID Assumption**:  
   The image ID assumed is `3639e9be-39de-4477-b807-cd32def33519`. Example URL:  
   [https://img.classistatic.de/api/v1/mo-prod/images/36/3639e9be-39de-4477-b807-cd32def33519](https://img.classistatic.de/api/v1/mo-prod/images/36/3639e9be-39de-4477-b807-cd32def33519)

3. **Valid Image Extensions**:  
   When saving an image to the database, we assume that image extensions are valid and are verified before saving.

4. **Listing ID**:  
   The listing ID is retrieved from a different module. It is passed as an input parameter in the remote data source to the API request.

---

## Testing Approach

The testing strategy follows the **Test Pyramid** principle, focusing on **unit tests** for core logic and fewer **E2E tests** for maintainability.

### Edge Cases Tested:
- Display proper error message when the API fails.
- Show message when no images are found in the gallery.
- Display message when no high-resolution image is available.
- Show a loading indicator when the gallery screen is loading.
- Show a loading indicator when the detail screen is loading.
- Display a retry button when an error occurs on gallery screen
- Ensure the back button on the gallery screen navigates to the gallery.
- Show an error message when the app is operated in offline.
- Handle HTTP or network errors gracefully.
- Test UI in both **dark** and **light** themes.
- Ensure the scroll view works when the image list is large.
- Test UI in both **portrait** and **landscape** modes.
- Ensure pressing back from the detail screen multiple times doesn’t crash the app (navigation stack behavior).
- Test app behavior with limited internet connectivity.
- Handle unsupported file formats gracefully.
- Ensure content descriptions are set for all images and icons for accessibility.

---

## Future Implementations

1. **UI Tests**
2. **End-to-End (E2E) Tests**

---

## User Journey to be Implemented as E2E Tests

1. **Gallery to Detail Navigation**:
    - User launches the app → gallery screen with images is displayed → user clicks on an image → detail screen is displayed → user clicks on the back button → gallery screen is displayed.

2. **Retry on Empty Gallery**:
    - User launches the app → no image is displayed in the gallery and an error message is shown → user clicks on the retry button → images are displayed in the gallery → user clicks on an image → detail screen is displayed → user clicks on the back button → gallery screen is displayed.

3. **Error in Detail Screen**:
    - User launches the app → gallery screen with images is displayed → user clicks on an image → no image is displayed in the detail screen → user clicks on the retry button → no image is displayed in the detail screen → user clicks on the back button → gallery screen is displayed.

4. **Retry on Empty Gallery (No Images)**:
    - User launches the app → no image is displayed in the gallery and an error message is displayed → user clicks on the retry button → no images are displayed in the gallery.

---

## UI Tests

1. **Loading Indicator**:  
   Ensure that a loading indicator is displayed when the gallery is in the loading state.

2. **Error Message**:  
   Display an error message when the gallery is in the error state.

3. **Gallery State Success**:  
   Verify that the gallery is displayed with the correct number of images when the state of the gallery is successful.

4. **Empty State in Gallery**:  
   Show an empty state when no images are found in the gallery.

5. **Detail Screen Navigation**:  
   When an image is clicked in the gallery, ensure the detail screen is displayed.

6. **Back Button Behavior**:  
   Ensure that pressing the back button in the detail screen navigates back to the gallery screen.

7. **Retry Button Behavior**:  
   Ensure that clicking the retry button reloads the gallery.

8. **Detail Screen State Success**:  
   Ensure that the detail screen is displayed when the state of the details is successful.

9. **Detail Screen Loading Indicator**:  
   Show a loading indicator in the detail screen when the state is loading.

10. **Error Message in Detail Screen**:  
    Display an error message in the detail screen when the state is error.

11. **Empty State in Detail Screen**:  
    Show an empty state when there is no high-resolution image to display in the detail screen.

---