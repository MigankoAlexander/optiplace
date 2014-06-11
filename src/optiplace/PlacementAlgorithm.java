/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optiplace;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Miganko
 */
public class PlacementAlgorithm {

    private int areaWidth;
    private int areaHeight;
    private int xDevStep;
    private int yDevStep;
    private int carsNumber;
    private int setsNumber;
    private int radius;
    private List<Point> incidents;

    public PlacementAlgorithm(int areaWidth, int areaHeight, int xDevStep,
            int yDevStep, int carsNumber, int setsNumber, int radius,
            List<Point> incidents) {
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.xDevStep = xDevStep;
        this.yDevStep = yDevStep;
        this.carsNumber = carsNumber;
        this.setsNumber = setsNumber;
        this.radius = radius;
        this.incidents = incidents;
    }
    
    

    public List<List<Point>> getInitSets() {

        List<List<Point>> initialSets = new ArrayList<>();
        List<List<Point>> potintialLocations = devideArea();
        Random random = new Random();

        for (int i = 0; i < setsNumber; i++) {

            initialSets.add(new ArrayList<Point>());

            for (int j = 0; j < carsNumber; j++) {
                int xRanNum = random.nextInt(potintialLocations.size());
                int yRanNum = random.nextInt(potintialLocations.get(0).size());
                initialSets.get(i).add(new Point(xRanNum * xDevStep,
                        yRanNum * yDevStep));
            }
        }
        return initialSets;
    }

    private List<List<Point>> devideArea() {
        List<List<Point>> potintialLocations = new ArrayList<>();

        for (int x = 0; x < areaWidth / xDevStep; x++) {
            potintialLocations.add(new ArrayList<Point>());
            for (int y = 0; y < areaHeight / yDevStep; y++) {
                potintialLocations.get(x).add(new Point(x * xDevStep, y * yDevStep));
            }
        }

        return potintialLocations;
    }

    public List<Double> getChances(List<List<Point>> sets) {
        List<Integer> differences = new ArrayList<>();
        List<Double> chances = new ArrayList<>();
        double sumOfInverse = 0;
        double bigEnoughNumber = 100.0;//in case of fully suitable set

        for (int i = 0; i < sets.size(); i++) {
            int coveredEmerg = 0;
            for (Point incident : incidents) {
                for (int j = 0; j < sets.get(i).size(); j++) {
                    if (inCircle(incident.x, incident.y, sets.get(i).get(j).x, sets.get(i).get(j).y, radius)) {
                        coveredEmerg++;
                        j = sets.get(i).size();
                    }
                }
            }
            differences.add(incidents.size() - coveredEmerg);
            if (incidents.size() != coveredEmerg) {
                sumOfInverse += 1.0 / (incidents.size() - coveredEmerg);
            } else {
                sumOfInverse += bigEnoughNumber;
            }
        }

        for (int i = 0; i < sets.size(); i++) {

            if (differences.get(i) != 0) {
                chances.add(Math.round(((1.0 / differences.get(i)) / sumOfInverse) * 1000.000) / 1000.000);
            } else {
                chances.add(Math.round((bigEnoughNumber / sumOfInverse) * 1000.000) / 1000.000);
            }



        }

        return chances;
    }

    public List<Integer> getUncoveredEmerg(List<List<Point>> sets) {

        List<Integer> uncoveredList = new ArrayList<>();

        for (int i = 0; i < sets.size(); i++) {
            int coveredEmerg = 0;
            for (Point incident : incidents) {
                for (int j = 0; j < sets.get(i).size(); j++) {
                    if (inCircle(incident.x, incident.y, sets.get(i).get(j).x, sets.get(i).get(j).y, radius)) {
                        coveredEmerg++;
                        j = sets.get(i).size();
                    }
                }
            }
            uncoveredList.add(incidents.size() - coveredEmerg);
        }
        return uncoveredList;
    }

    public boolean inCircle(int xEmerg, int yEmerg, int xCircle,
            int yCircle, int radius) {

        if ((Math.pow(xEmerg - xCircle, 2) + Math.pow(yEmerg - yCircle, 2)) <= (radius * radius)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Point> getPairs(List<Double> chances) {
        List<Point> pairs = new ArrayList<>();

        List<Double> sortedChances = new ArrayList<>();
        List<Integer> oldPlaces = new ArrayList<>();
        for (int i = 0; i < chances.size(); i++) {
            sortedChances.add(chances.get(i));
            oldPlaces.add(i);
        }

        for (int i = 0; i < sortedChances.size(); i++) {
            for (int j = 0; j < sortedChances.size() - i - 1; j++) {
                if (sortedChances.get(j) > sortedChances.get(j + 1)) {
                    double temp = sortedChances.get(j);
                    sortedChances.set(j, sortedChances.get(j + 1));
                    sortedChances.set(j + 1, temp);

                    temp = oldPlaces.get(j);
                    oldPlaces.set(j, oldPlaces.get(j + 1));
                    oldPlaces.set(j + 1, (int) temp);
                }
            }


        }


        Random random = new Random();

        while (pairs.size() != setsNumber) {

            int randomInterval = 0;

            for (int l = 0; l < sortedChances.size(); l++) {
                randomInterval += sortedChances.get(l) * 100;
            }
            int firstNum = random.nextInt(randomInterval);
            int secondNum = random.nextInt(randomInterval);

            int tempSum1 = 0;

            for (int j = 0; j < setsNumber; j++) {
                tempSum1 += sortedChances.get(j) * 100;
                if (firstNum < tempSum1) {
                    int tempSum2 = 0;
                    for (int k = 0; k < setsNumber; k++) {
                        tempSum2 += sortedChances.get(k) * 100;
                        if (secondNum < tempSum2 && oldPlaces.get(j) != oldPlaces.get(k)) {
                            pairs.add(new Point(oldPlaces.get(j), oldPlaces.get(k)));
                            k = sortedChances.size();
                        }
                    }
                    j = sortedChances.size();
                }
            }
        }

        return pairs;
    }

    public int getNumOfMax(List<Double> chances) {
        int numOfMax = -1;
        double maxChance = -1.0;
        for (int i = 0; i < chances.size(); i++) {
            if (chances.get(i) > maxChance) {
                maxChance = chances.get(i);
                numOfMax = i;
            }
        }
        return numOfMax;
    }

    public int getMin(List<Integer> uncovered) {
        int minNum = Integer.MAX_VALUE;
        for (int i = 0; i < uncovered.size(); i++) {
            if (uncovered.get(i) < minNum) {
                minNum = uncovered.get(i);

            }
        }
        return minNum;
    }

    public List<List<Point>> getNextGen(List<List<Point>> previousGens, List<Point> pairs) {
        List<List<Point>> nextGen = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < setsNumber; i++) {
            int separator = random.nextInt(carsNumber);

            List<Point> newSet = new ArrayList<>();
            for (int j = 0; j < separator; j++) {
                newSet.add(previousGens.get(pairs.get(i).x).get(j));
            }
            for (int j = separator; j < carsNumber; j++) {
                newSet.add(previousGens.get(pairs.get(i).y).get(j));
            }
            nextGen.add(newSet);
        }

        return nextGen;
    }

    public boolean compareGens(List<List<Point>> parents, List<List<Point>> nextGen) {

        List<Integer> uncoveredPar = getUncoveredEmerg(parents);
        List<Integer> uncoveredNext = getUncoveredEmerg(nextGen);

        double averegePar = 0.0;
        double averegeNext = 0.0;

        for (int i = 0; i < uncoveredPar.size(); i++) {
            averegePar += uncoveredPar.get(i);
        }
        averegePar /= uncoveredPar.size();

        for (int i = 0; i < uncoveredNext.size(); i++) {
            averegeNext += uncoveredNext.get(i);
        }
        averegeNext /= uncoveredNext.size();


        if (averegeNext < averegePar) {
            return true;
        } else {
            return false;
        }
    }

    public double getFitness(List<List<Point>> list) {

        List<Integer> uncoveredPar = getUncoveredEmerg(list);
        double averege = 0.0;

        for (int i = 0; i < uncoveredPar.size(); i++) {
            averege += uncoveredPar.get(i);
        }
        averege /= uncoveredPar.size();

        return averege;

    }

    public List<List<Point>> mutation(List<List<Point>> nextGen) {
        Random random = new Random();

        for (int i = 0; i < nextGen.size(); i++) {

            int minInCircles = getMinInCircles(nextGen.get(i));

            for (int j = 0; j < nextGen.get(i).size(); j++) {

                if (circleContainMin(nextGen.get(i).get(j), minInCircles)) {

                    if (circleEmpty(nextGen.get(i).get(j))) {
                        int ranEmerg1 = random.nextInt(incidents.size());
                        int ranEmerg2 = random.nextInt(incidents.size());

                        Point newPoint = new Point((incidents.get(ranEmerg1).x
                                + incidents.get(ranEmerg2).x) / 2,
                                (incidents.get(ranEmerg1).y
                                + incidents.get(ranEmerg2).y) / 2);
                        nextGen.get(i).set(j, newPoint);


                    } else {
                        nextGen.get(i).set(j, getNewPoint(nextGen.get(i).get(j)));
                    }
                    j = nextGen.get(i).size();
                }

            }
        }

        return nextGen;
    }

    private boolean circleContainMin(Point center, int min) {
       
        int numOfEmerg = 0;
        for (int i = 0; i < incidents.size(); i++) {
            if (inCircle(incidents.get(i).x, incidents.get(i).y, center.x, center.y, radius)) {
                numOfEmerg++;
            }
        }
        if (numOfEmerg == min) {
            return true;
        }
        return false;
    }

    private boolean circleEmpty(Point center) {
        for (int i = 0; i < incidents.size(); i++) {
            if (inCircle(incidents.get(i).x, incidents.get(i).y, center.x, center.y, radius)) {
                return false;
            }
        }

        return true;
    }

    private int getMinInCircles(List<Point> set) {

        List<Integer> emergInCircle = new ArrayList<>();

        for (Point center : set) {
            int numOfEmerg = 0;
            for (int i = 0; i < incidents.size(); i++) {
                if (inCircle(incidents.get(i).x, incidents.get(i).y, center.x, center.y, radius)) {
                    numOfEmerg++;
                }
            }
            emergInCircle.add(numOfEmerg);
        }

        int min = getMin(emergInCircle);

        return min;
    }

    private Point getNewPoint(Point center) {

        Random random = new Random();
        Point ranEmerg = incidents.get(random.nextInt(incidents.size()));

        int averegeX = 0;
        int averegeY = 0;
        int numOfPoints = 1;

        for (int i = 0; i < incidents.size(); i++) {
            if (inCircle(incidents.get(i).x, incidents.get(i).y, center.x, center.y, radius)) {
                averegeX += incidents.get(i).x;
                averegeY += incidents.get(i).y;
                numOfPoints++;
            }
        }

        averegeX += ranEmerg.x;
        averegeY += ranEmerg.y;




        Point newPoint = new Point(averegeX /= numOfPoints, averegeY /= numOfPoints);

        return newPoint;
    }

    public void optimaze(List<Point> set) {
        Random random = new Random();
        
        for (int i = 0; i < set.size(); i++) {

            List<Point> emergInCircle = getEmergInCircle(set.get(i));

            if (circleEmpty(set.get(i))) {
                int rand = random.nextInt(incidents.size());

                set.get(i).x = incidents.get(rand).x + 5;
                set.get(i).y = incidents.get(rand).y + 5;

            } else {
                if (emergInCircle.size() == 1) {
                    set.get(i).x = emergInCircle.get(0).x + 5;
                    set.get(i).y = emergInCircle.get(0).y + 5;

                } else {

                    int averegeX = 0;
                    int averegeY = 0;

                    for (int j = 0; j < emergInCircle.size(); j++) {
                        averegeX += emergInCircle.get(j).x;
                        averegeY += emergInCircle.get(j).y;
                    }
                    set.get(i).x = averegeX /= emergInCircle.size();
                    set.get(i).y = averegeY /= emergInCircle.size();
                }
            }
        }
        for (int k = 0; k < set.size(); k++) {
            for (int i = k+1; i < set.size(); i++) {
                if(set.get(k).x == set.get(i).x && set.get(k).y == set.get(i).y){
                    set.get(i).x += 5;
                    set.get(i).y += 5;
                }
            }
        }
    }

    private List<Point> getEmergInCircle(Point center) {
        List<Point> emergInCircle = new ArrayList<>();

        for (int i = 0; i < incidents.size(); i++) {
            if (inCircle(incidents.get(i).x, incidents.get(i).y, center.x, center.y, radius)) {
                emergInCircle.add(incidents.get(i));
            }
        }
        return emergInCircle;
    }
}
