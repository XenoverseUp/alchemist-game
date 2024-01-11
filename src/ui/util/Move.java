package ui.util;

import java.awt.Rectangle;

import javax.swing.JComponent;

public class Move {
    private int direction = 0;
    private JComponent component;
    private Rectangle from, to;

    public Move(JComponent component) {
        this.component = component;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Move from(Rectangle from) {
        this.from = from;
        return this;
    }

    public Move to(Rectangle to) {
        this.to = to;
        return this;
    }

    private double square(double x) {
        return x * x;
    }

    private double flip(double x) {
        return 1 / x;
    }

    /** Possible easings for the modal. */

    private double easeOut(double x) {
        return flip(square(flip(x)));
    }

    private double easeIn(double x) {
        return square(x);
    }

    private double easeInOut(double x) {
        return lerp(easeIn(x), easeOut(x), x);
    }

    /**
     * @param x ranges between 0 and 1.
     * @returns interpolated value in the range.
     */
    private double lerp(double start, double end, double progress) {
        return (start + (end - start) * progress);
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int range =  from.y - to.y;

                 for (int i = 0; i < range; i += 3) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                    }
                    if (direction == 0) 
                        component.setBounds(from.x, from.y - (int)(lerp(0, range, easeInOut((double)i / range))), from.width, from.height);
                    else
                        component.setBounds(to.x, to.y + (int)(lerp(0, range, easeInOut((double)i / range))), to.width, to.height);

                }
            }
        }).start();;
    }


}