package edu.jsu.mcis.cs310.tas_sp25;
// **Shared Rounding Logic for Weekend Punches and Other Adjustments**
        int minute = original.getMinute();
        int remainder = minute % roundInterval;
        int adjustment;

        // Apply the rounding logic only once
        if (remainder < roundInterval / 2) {
            adjustment = -remainder; // Round down
        } else {
            adjustment = roundInterval - remainder; // Round up
        }

        // Skip Adjustment for Weekend Punches (same rounding logic)
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            adjusted = original.plusMinutes(adjustment).withSecond(0).withNano(0);
            adjustmentType = PunchAdjustmentType.INTERVAL_ROUND;
            this.adjustedTimeStamp = adjusted;
            this.adjustmentType = adjustmentType;
            return;
        }