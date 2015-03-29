Configuration based data json model:

No

Instead:

Top level classes to implement configuration and some logic.


class Star {
  String color;
  int size;
  Position pos;
}


List<Star> generateStars(StarsFactory sf) {
    List<Position> allPos = sf.createStarMap();
    for (allPos

}

class GalaxySettings {
    enum


}

class GalaxyGenerator {
    StarsGenerator starsGenerator;
    StarMapGenerator starMapGenerator;

    List<Star> generate(GalaxySettings settings) {
        int numStars = 20;
        SizeF galaxySize = new SizeF(100, 100);
        List<Star> starsGenerator.genreate

    }


}

class StarsGenerator {

class StarsFactory {

}
