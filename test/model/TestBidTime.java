package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.prg3.mr_bid.model.entity.BidDate;
import com.prg3.mr_bid.model.entity.BidTime;

public class TestBidTime {

	public static void main(String[] args) {
		BidDate date = new BidDate(19, 5, 2019);
		Calendar c = new GregorianCalendar();
		BidTime bidTime = new BidTime(date, (short) 0);
		
		System.out.println(date.getDateString());
		System.out.println(c.get(c.DAY_OF_MONTH));
		System.out.println(c.get(c.MONTH));
		System.out.println(bidTime.getTimeOnDays());
		System.out.println(bidTime.getTimeOnHours());
	}

}
