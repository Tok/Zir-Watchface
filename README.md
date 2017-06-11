# Zir-Watchface
...work in progress...

## Screenshots

![Elastic Geometry](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Screenshots/elastic_geometry.png "Elastic Geometry")
![Elastic Outline](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Screenshots/elastic_outline.png "Elastic Outline")
![Green Triangle](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Screenshots/green_triangle.png "Green Triangle")
![Hands and Circles](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Screenshots/hands_and_circles.png "Hands and Circles")
![Red Geometry](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Screenshots/red_geometry.png "Red Geometry")

---

![Preview](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Wearable/src/main/res/drawable-hdpi/preview_zir.png "Preview")
![Circles](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Wearable/src/main/res/drawable-hdpi/theme_circles.png "Circles")
![Fields](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Wearable/src/main/res/drawable-hdpi/theme_fields.png "Fields")
![Geometry](https://raw.githubusercontent.com/Tok/Zir-Watchface/master/Wearable/src/main/res/drawable-hdpi/theme_geometry.png "Geometry")

---

## TODO

### High Priority
* ~~Move all the variable config data into the ConfigData object.~~
* ~~Elasticy~~
* Separate color selection from palette and allow individual selection.
* Create a setting to turn the seconds on or off.
* ~~Replace the ambient mode implementation with a simple color filter and use lower refresh rate~~ 
* Reduce redundancies and make code more idiomatic.

### Medium Priority
* ~~Use generated colorshades and apply them dynamically.~~
* Unlock the center dot from the center of the watchface and find a way to display the time by just rendering a distorted tetrahedron.
* Center dot should be visually different from the hands, this may be done by using a darker or brighter color.
* Find a workaround for the problem when all the dots are lined up and the tetrahedron would be a tangent.
* Consider adding 12 dynamic helplines from the center of the center dot, in case it is unlocked from the center of the watchface.

### Low Priority
* Use a blockchain technology to introduce an absolute artifical timesource, that solves all the relativistic issues, where observers in different systems may observe a different times and orders.
* It should be based on a proof of work algorithm, because the energy spent for finding the block, directly correlates to the mass avaliable in the system that signs it.
* Reduce the blocksize down to the planck-time.
