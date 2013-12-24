# Alloy Generator

A code generator developed to help generating taglibs and facelets based on AlloyUI inline code documentaiton.

![Preview](http://s17.postimg.org/4a9rf4nq7/Screen_Shot_2013_12_23_at_8_32_57_PM.png)


## How to setup?

Just create your build.{username}.properties and override the properties you want from the [build.properties](build.properties).

## How to generate?

To run the generator for the first time, no configuration is needed. Simply type:

```sh
$ ant build-taglibs
```

And it's gonna generate code fore you under the folder [build](build)
