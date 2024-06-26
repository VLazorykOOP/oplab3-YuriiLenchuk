import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Prototype pattern
abstract class Shape implements Cloneable {
    private String id;
    protected String type;

    abstract void draw();

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

class Circle extends Shape {
    public Circle() {
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Square extends Shape {
    public Square() {
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Drawing a Square");
    }
}

class ShapeCache {
    private static final Map<String, Shape> shapeMap = new HashMap<>();

    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(), circle);

        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(), square);
    }
}

// Adapter pattern
interface AdvancedShapeDrawer {
    void drawCircle();
    void drawSquare();
}

class AdvancedShapeDrawerImpl implements AdvancedShapeDrawer {
    @Override
    public void drawCircle() {
        System.out.println("Drawing an advanced Circle");
    }

    @Override
    public void drawSquare() {
        System.out.println("Drawing an advanced Square");
    }
}

interface ShapeDrawer {
    void draw(String shapeType);
}

class ShapeDrawerAdapter implements ShapeDrawer {
    AdvancedShapeDrawer advancedShapeDrawer;

    public ShapeDrawerAdapter(String shapeType) {
        if (shapeType.equalsIgnoreCase("circle")) {
            advancedShapeDrawer = new AdvancedShapeDrawerImpl();
        } else if (shapeType.equalsIgnoreCase("square")) {
            advancedShapeDrawer = new AdvancedShapeDrawerImpl();
        }
    }

    @Override
    public void draw(String shapeType) {
        if (shapeType.equalsIgnoreCase("circle")) {
            advancedShapeDrawer.drawCircle();
        } else if (shapeType.equalsIgnoreCase("square")) {
            advancedShapeDrawer.drawSquare();
        }
    }
}

// Observer pattern
class Subject {
    private final List<Observer> observers = new ArrayList<>();
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

abstract class Observer {
    protected Subject subject;
    public abstract void update();
}

class ConcreteObserver extends Observer {
    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("Observer notified with state: " + subject.getState());
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        // Prototype pattern
        ShapeCache.loadCache();

        Shape clonedCircle = ShapeCache.getShape("1");
        System.out.println("Shape: " + clonedCircle.getType());
        clonedCircle.draw();

        Shape clonedSquare = ShapeCache.getShape("2");
        System.out.println("Shape: " + clonedSquare.getType());
        clonedSquare.draw();

        // Adapter pattern
        ShapeDrawer shapeDrawer = new ShapeDrawerAdapter("circle");
        shapeDrawer.draw("circle");

        shapeDrawer = new ShapeDrawerAdapter("square");
        shapeDrawer.draw("square");

        // Observer pattern
        Subject subject = new Subject();
        new ConcreteObserver(subject);

        subject.setState("State 1");
        subject.setState("State 2");
    }
}
