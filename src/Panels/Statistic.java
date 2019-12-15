package Panels;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.*;

public class Statistic {
	SimpleStringProperty subject;
	SimpleDoubleProperty minimum;
	SimpleDoubleProperty median;
	SimpleDoubleProperty mean;
	SimpleDoubleProperty maximum;
	SimpleDoubleProperty deviation;
	
	public Statistic(String subject,
					Double minimum,
					Double median,
					Double mean,
					Double maximum,
					Double deviation) {
		
		this.subject = new SimpleStringProperty(subject);
		this.minimum = new SimpleDoubleProperty(minimum);
		this.median = new SimpleDoubleProperty(median);
		this.mean = new SimpleDoubleProperty(mean);
		this.maximum = new SimpleDoubleProperty(maximum);
		this.deviation = new SimpleDoubleProperty(deviation);
		
	}
	
	public String getSubject() {
		return subject.get();
	}
	public void setSubject(String subject) {
		this.subject = new SimpleStringProperty(subject);
	}
	public Double getMinimum() {
		return minimum.get();
	}
	public void setMinimum(Double minimum) {
		this.minimum = new SimpleDoubleProperty(minimum);
	}
	public Double getMedian() {
		return median.get();
	}
	public void setMedian(Double median) {
		this.median = new SimpleDoubleProperty(median);
	}
	public Double getMean() {
		return mean.get();
	}
	public void setMean(Double mean) {
		this.mean = new SimpleDoubleProperty(mean);
	}
	public Double getMaximum() {
		return maximum.get();
	}
	public void setMaximum(Double maximum) {
		this.maximum = new SimpleDoubleProperty(maximum);
	}
	public Double getDeviation() {
		return deviation.get();
	}
	public void setDeviation(Double deviation) {
		this.deviation = new SimpleDoubleProperty(deviation);
	}

		
}
