package com.seal.util.dto;

import com.seal.util.StaticUtil;

import java.util.Objects;

/**
 * Created by seal on 4/4/2017.
 */
public class ObjectiveFunctionsValues {
    public final double load;
    public final long keyPress;
    public final long handAlternation;
    public final double distance;
    public final double bigStepDistance;
    public final long hitDirection;

    private ObjectiveFunctionsValues(double load, long keyPress, long handAlternation, double distance, double bigStepDistance, long hitDirection) {
        this.load = load;
        this.keyPress = keyPress;
        this.handAlternation = handAlternation;
        this.distance = distance;
        this.bigStepDistance = bigStepDistance;
        this.hitDirection = hitDirection;
    }

    //--------------- Global Score ---------------//
    public double globalScore(ObjectiveFunctionsValues refKeyboard) {
        if (Objects.isNull(refKeyboard)) {
            throw new RuntimeException("Ref Keyboard is Null");
        }
        double load = (this.load * StaticUtil.WeightCoefficients.Load_And_Accessibility.getCoefficient()) / refKeyboard.load;
        double keyPress = (this.keyPress * StaticUtil.WeightCoefficients.Key_Number.getCoefficient()) / refKeyboard.keyPress;
        double handAlternation = (this.handAlternation * StaticUtil.WeightCoefficients.Hand_Alternation.getCoefficient()) / refKeyboard.handAlternation;
        double distance = (this.distance * StaticUtil.WeightCoefficients.Consecutive_Usage_Of_Same_Finger.getCoefficient()) / refKeyboard.distance;
        double bigStepDistance = (this.bigStepDistance * StaticUtil.WeightCoefficients.Avoid_Steps.getCoefficient()) / refKeyboard.bigStepDistance;
        double hitDirection = (this.hitDirection * StaticUtil.WeightCoefficients.Hit_Direction.getCoefficient()) / refKeyboard.hitDirection;

        return load + keyPress + handAlternation + distance + bigStepDistance + hitDirection;
    }


    //--------------- toString---------------//

    @Override
    public String toString() {
        return "ObjectiveFunctionsValues{" +
                "load=" + load +
                ", keyPress=" + keyPress +
                ", handAlternation=" + handAlternation +
                ", distance=" + distance +
                ", bigStepDistance=" + bigStepDistance +
                ", hitDirection=" + hitDirection +
                '}';
    }


    //--------------- Builder ---------------//

    public static class Builder {
        private double load;
        private long keyPress;
        private long handAlternation;
        private double distance;
        private double bigStepDistance;
        private long hitDirection;

        public Builder setLoad(double load) {
            this.load = load;
            return this;
        }

        public Builder setKeyPress(long keyPress) {
            this.keyPress = keyPress;
            return this;
        }

        public Builder setHandAlternation(long handAlternation) {
            this.handAlternation = handAlternation;
            return this;
        }

        public Builder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder setBigStepDistance(double bigStepDistance) {
            this.bigStepDistance = bigStepDistance;
            return this;
        }

        public Builder setHitDirection(long hitDirection) {
            this.hitDirection = hitDirection;
            return this;
        }

        public ObjectiveFunctionsValues build() {
            return new ObjectiveFunctionsValues(load, keyPress, handAlternation, distance, bigStepDistance, hitDirection);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
