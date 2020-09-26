## Test on Real Android Devices

1. Enable Developer Options and USB Debugging

    - <https://developer.android.com/studio/debug/dev-options>

2. Connect the device with USB cable to the PC

3. [Optional] Keep device awake while charging

    In order to prevent device lock during test run you can change `Stay Awake` settings, please see at the end of [this blog post](https://support.robinpowered.com/hc/en-us/articles/360038170072-How-to-keep-your-Android-device-from-going-to-sleep).

4. Verify device is found by Android Debug Bridge

    You can verify steps above by running following command:

    ```cmd
    %ANDROID_HOME%\platform-tools\adb devices
    ```

    If everything is ok it should return something like:

    ```cmd
    List of devices attached
    RF8N715NJ0B     device
    ```

    More Details on `adb` command line:
    - <https://developer.android.com/studio/debug/dev-options#enable>
