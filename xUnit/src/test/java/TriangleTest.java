import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TriangleTest {
    private static Triangle triangle;

    @BeforeClass
    public static void setupTriangle() {
        triangle = new Triangle();
    }

    @DataProvider
    public static Object[][] positiveCase() {
        return new Object[][] {
                {3.2, 4.1, 4.25},
                {14, 6, 9},
                {8, 13, 12.5}
        };
    }

    @DataProvider
    public static Object[][] negativeCase() {
        return new Object[][] {
                {3, 4, 1},
                {14, 4, 5},
                {8, 13, 2}
        };
    }

    @DataProvider
    public static Object[][] bigSides() {
        return new Object[][] {
                {111111111111d, 111111111111d, 111111111111d},
                {123456789123d, 123456789123d, 123456789123d},
        };
    }

    @DataProvider
    public static Object[][] smallSides() {
        return new Object[][] {
                {0.00000002d, 0.00000004d, 0.00000003d},
                {Double.MIN_VALUE * 3, Double.MIN_VALUE * 4, Double.MIN_VALUE * 5},
        };
    }

    @DataProvider
    public static Object[][] zeroSides() {
        return new Object[][] {
                {3, 0, 4},
                {0, 0, 0},
                {0, 0, 1}
        };
    }

    @DataProvider
    public static Object[][] negativeSides() {
        return new Object[][] {
                {3, -10, 4},
                {6, 100, -5},
                {55, -20, -1}
        };
    }

    @DataProvider
    public static Object[][] NaNSides() {
        return new Object[][] {
                {3, Double.NaN, 4},
                {Double.NaN, 6, Double.NaN},
                {Double.NaN, Float.NaN, Double.NaN}
        };
    }

    @DataProvider
    public static Object[][] equilateralTriangle() {
        return new Object[][] {
                {3, 3, 3},
                {100, 100, 100},
                {11, 11, 11}
        };
    }

    @DataProvider
    public static Object[][] isoscelesTriangle() {
        return new Object[][] {
                {3, 5, 3},
                {1013, 1013, 100},
                {5, 11, 11}
        };
    }

    @DataProvider
    public static Object[][] rightTriangle() {
        return new Object[][] {
                {3, 4, 5},
                {20, 16, 12},
                {8, 6, 10}
        };
    }

    @DataProvider
    public static Object[][] scaleneTriangle() {
        return new Object[][] {
                {7, 9, 10},
                {16, 17, 18},
                {21, 26, 35}
        };
    }

    @Test(dataProvider = "positiveCase")
    public void testIsValidTrianglePositive(double a, double b, double c) throws Exception {
        Assert.assertTrue(triangle.isValidTriangle(a, b, c));
    }

    @Test(dataProvider = "negativeCase",
            expectedExceptions = TriangleException.class,
            expectedExceptionsMessageRegExp = "It is not a triangle!")
    public void testIsValidTriangleNegative(double a, double b, double c) throws Exception {
        triangle.typeOfTriangle(a, b, c);
    }

    @Test(dataProvider = "bigSides")
    public void testIsValidTriangleBigSides(double a, double b, double c) throws Exception {
        Assert.assertTrue(triangle.isValidTriangle(a, b, c));
    }

    @Test(dataProvider = "smallSides")
    public void testIsValidTriangleSmallSides(double a, double b, double c) throws Exception {
        Assert.assertTrue(triangle.isValidTriangle(a, b, c));
    }

    @Test(dataProvider = "zeroSides",
            expectedExceptions = TriangleException.class,
            expectedExceptionsMessageRegExp = "Sides can't be zero!")
    public void testIsValidTriangleZeroSides(double a, double b, double c) throws Exception {
        triangle.isValidTriangle(a, b, c);
    }

    @Test(dataProvider = "negativeSides",
            expectedExceptions = TriangleException.class,
            expectedExceptionsMessageRegExp = "Sides can't be negative!")
    public void testIsValidTriangleNegativeSides(double a, double b, double c) throws Exception {
        triangle.isValidTriangle(a, b, c);
    }

    @Test(dataProvider = "NaNSides",
            expectedExceptions = TriangleException.class,
            expectedExceptionsMessageRegExp = "Sides can't be NaN!")
    public void testIsValidTriangleNaNSides(double a, double b, double c) throws Exception {
        triangle.isValidTriangle(a, b, c);
    }

    @Test(dataProvider = "equilateralTriangle")
    public void testEquilateralTriangle(double a, double b, double c) throws Exception {
        Assert.assertEquals(triangle.typeOfTriangle(a, b, c), "Equilateral");
    }

    @Test(dataProvider = "isoscelesTriangle")
    public void testIsoscelesTriangle(double a, double b, double c) throws Exception {
        Assert.assertEquals(triangle.typeOfTriangle(a, b, c), "Isosceles");
    }

    @Test(dataProvider = "rightTriangle")
    public void testRightTriangle(double a, double b, double c) throws Exception {
        Assert.assertEquals(triangle.typeOfTriangle(a, b, c), "Right");
    }

    @Test(dataProvider = "scaleneTriangle")
    public void testScaleneTriangle(double a, double b, double c) throws Exception {
        Assert.assertEquals(triangle.typeOfTriangle(a, b, c), "Scalene");
    }
}
