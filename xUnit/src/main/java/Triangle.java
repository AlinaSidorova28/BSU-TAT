public class Triangle {

    public boolean isValidTriangle(double a, double b, double c) throws TriangleException {
        if (a == 0 || b == 0 || c == 0) {
            throw  new TriangleException("Sides can't be zero!");
        } else if (a < 0 || b < 0 || c < 0) {
            throw new TriangleException("Sides can't be negative!");
        } else if ( Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c)){
            throw new TriangleException("Sides can't be NaN!");
        }

        return (a + b) > c && (a + c) > b && (b + c) > a;
    }

    public String typeOfTriangle(double a, double b, double c) throws TriangleException{
        Triangle triangle = new Triangle();
        if (triangle.isValidTriangle(a,b,c)) {
            if (a == b && b == c) {
                return "Equilateral";
            }
            if (a == b || b == c || a == c) {
                return "Isosceles";
            }
            if ( (a*a + b*b == c*c) || (a*a + c*c == b*b) || (c*c + b*b == a*a)) {
                return "Right";
            }

            return "Scalene";
        } else
            throw new TriangleException("It is not a triangle!");
    }
}
