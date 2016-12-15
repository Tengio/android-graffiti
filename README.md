Android Graffiti
================

Thin library to overlay and draw over an image.

Currently Graffiti is based on fresco and uses Postprocessors to draw on images.

Examples
--------

![alt tag](https://raw.githubusercontent.com/Tengio/android-graffiti/master/resources/a_drunk_developer.png)


Version
-------

//TODO
[ ![Download](https://api.bintray.com/packages/tengioltd/maven/graffiti/images/download.svg) ](https://bintray.com/tengioltd/maven/graffiti/_latestVersion)


HOW TO
======

Dependencies
------------

```
dependencies {
    ...
    compile('com.tengio.android:graffiti:latest_version')
    ...
}
```

By adding locations library dependency you will automatically get the following dependencies:

```
compile 'com.facebook.fresco:fresco:0.14.1'
```


Use of PostprocessorPainter
---------------------------

First you prepare the uri for the image request as you normally will do for fresco.
```
Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.species_accipiter_brevipes))
                .build();
```

The next step is the usage of the PostprocessorPainter to attach the view and the list of objects you want to paint on the image.

```
        PostprocessorPainter.build(overlayView, uri, Arrays.asList(
        TODO
```


Type of object you can use to draw
----------------------------------

TODO



Library updates
---------------

We use bintray to deploy changes to jcenter. To deploy a new version make sure to define BINTRAY_USER and BINTRAY_KEY variables. Then run:

```
gradle bintrayUpload
```