package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;
import com.techelevator.view.Menu;

public class CampgroundCLI {
	

	private static final String MAIN_MENU_OPTION_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String MAIN_MENU_OPTION_RESERVATION = "Search For Reservation";
	private static final String MAIN_MENU_OPTION_RETURN = "Return to previous screen";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_VIEW_CAMPGROUNDS, 
																	 MAIN_MENU_OPTION_RESERVATION, 
																	 MAIN_MENU_OPTION_RETURN};

	private static final String RESERVATION_MENU_AVAILABLE = "Search for Available Reservation";
	private static final String RESERVATION_MENU_RETURN = "Return to previous screen";
	private static final String[] RESERVATION_MENU_OPTIONS = new String[] { RESERVATION_MENU_AVAILABLE, RESERVATION_MENU_RETURN};



	private ParkDAO parkDao;
	private CampgroundDAO campgroundDao;
	private SiteDAO siteDao;
	private ReservationDAO reservationDao;
	private Menu menu;
	List<Campground> campgrounds = null;
	List<Site> mySites = null;
	String choice;
	Park myPark;
	Site mySite;
	Campground myCampground;
	LocalDate arrivalLocalDate;
	LocalDate departureLocalDate;
	Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDao = new JDBCParkDAO(datasource);
		campgroundDao = new JDBCCampgroundDAO(datasource);
		siteDao = new JDBCSiteDAO(datasource);
		reservationDao = new JDBCReservationDAO(datasource);
		this.menu = new Menu(System.in, System.out);
	}

	public void run() {
		while(true) {
			System.out.println("View Parks Interface");
			System.out.println("Select a Park for Further Details: ");
			List<Park> parkList = parkDao.getAllParks();
			
			String [] parkNameArray = new String [parkList.size() + 1];
			for(int x = 0; x < parkList.size(); x++) {
				parkNameArray[x] = parkList.get(x).getName();
			}
			parkNameArray[parkNameArray.length - 1] = "Quit";
			
			choice = (String)menu.getChoiceFromOptions(parkNameArray);
			for (int x = 0; x < parkList.size(); x++) {
				if(choice.equals(parkNameArray[parkNameArray.length - 1])){
					System.out.println("Quitting...");
					System.exit(0);
				}
				if(choice.equals(parkList.get(x).getName())){
					myPark = parkDao.getParkByName(choice);
					runParkInformation();
				}
			}
		
		}
	}

	private void runParkInformation() {
		System.out.println("Park Information Screen");
		System.out.println("============================");
		System.out.println(myPark.getName() + " National Park");
		System.out.printf("%-25s %15s\n", "Location: " , myPark.getLocation());
		System.out.printf("%-25s %15s\n", "Established: " , myPark.getEstablishDate());
		System.out.printf("%-25s %15s\n", "Area: " , myPark.getArea() + " sq km");
		System.out.printf("%-25s %15s\n", "Annual Visitors: " , myPark.getVisitors());
		System.out.println("");
		System.out.println(myPark.getDescription());
		System.out.println(" ");
		System.out.println("Select a Command: ");
		boolean isTrue = false;
		while(!isTrue) {
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTION_VIEW_CAMPGROUNDS)) {
				displayCampgrounds();
				System.out.println(" ");
				runReservation();
			} else if(choice.equals(MAIN_MENU_OPTION_RESERVATION)) {
				runReservation();
			} else if(choice.equals(MAIN_MENU_OPTION_RETURN)) {
				isTrue = true;
				run();
			}
		
		}
	}

	private void runReservation() {
		boolean isTrue = false;
		while(!isTrue) {
			String choice = (String)menu.getChoiceFromOptions(RESERVATION_MENU_OPTIONS);
			if(choice.equals(RESERVATION_MENU_AVAILABLE)) {
				System.out.println("Search for Campground Reservation: ");
				displayCampgrounds();
				System.out.println(" ");
				boolean isFalse = false;
				String campground = null;
				int campgroundId = 0;
				while (!isFalse) {
					boolean shouldRun = false;
					while (!shouldRun) {
						try {
							System.out.println("Which campground (enter 0 to cancel)?");
							campground = input.nextLine();
							campgroundId = Integer.parseInt(campground);
							if (campgroundId != 0) {
								myCampground = campgroundDao.getCampgroundByCampgroundId(campgroundId, myPark.getId());
								for (int x = 0; x < campground.length(); x++) {
									if (campgroundId != myCampground.getId() && campgroundId != 0) {
										System.out.println("You did not input a valid Campground ID");
									}else {
										shouldRun = true;
										isFalse = true;
									}
								}
							}else {
								runReservation();
							}
						}catch (NullPointerException e) {
							System.out.println("You did not input a valid Campground ID");
						}catch (NumberFormatException e) {
							System.out.println("You did not input a valid Campground ID");
						}
					}
				}
				if (!campground.equals("0")) {
					int size = 0;
					while (size == 0) {
						boolean shouldRun = false;
						while (!shouldRun) {
							try {
								System.out.println("What is the arrival date? (yyyy-mm-dd)");
								String arrivalDate = input.nextLine();
								
								System.out.println("What is the departure date? (yyyy-mm-dd)");
								String departureDate = input.nextLine();
								arrivalLocalDate = LocalDate.parse(arrivalDate);
								departureLocalDate = LocalDate.parse(departureDate);
								if (departureLocalDate.isAfter(arrivalLocalDate)) {
									shouldRun = true;
								}else {
									System.out.println("Your departure date is on or before your arrival date. This is not possible. Please try again.");
								}
							}catch (DateTimeParseException e) {
								System.out.println("You did not enter the right date format. Please try again.");
							}catch (NumberFormatException e) {
								System.out.println("You did not enter the right date format. Please try again.");
							}
							
						}
						
						
						
						Long daysBetween = ChronoUnit.DAYS.between(arrivalLocalDate, departureLocalDate);
						Double cost = daysBetween * myCampground.getDailyFee();
						
						System.out.println(" ");
						System.out.println("Results Matching Your Search Criteria: ");
						System.out.printf("%-40s %-15s %-20s %-20s %-20s %-20s %-20s","Campground", "Site No.", "Max Occup.", "Accessible?", "Max RV Length",
								"Utility", "Cost");
						System.out.println("\n");
						System.out.println("==========================================================================================="
								+ "===============================================================================");
						
						
						List<Reservation> myReservations = reservationDao.getReservationsOverlappingStay(campgroundId, 
								arrivalLocalDate, departureLocalDate);
						
						mySites = siteDao.getAllSitesByCampgroundId(campgroundId);
						
						for (int x = 0; x < mySites.size(); x++) {
							for(int i = 0; i < myReservations.size(); i ++) {
								if(mySites.get(x).getId() == myReservations.get(i).getSiteId()) {
									mySites.remove(x);
								}
							}
						}
						size = mySites.size();
						if (size >= 5) {
							for(int x = 0; x < 5; x++) {
								String yesNo = null;
								String noYes = null;
								String maxRVLength = null;
								if(mySites.get(x).isAccessible() == true) {
									yesNo = "Yes";
								}else {
									yesNo = "No";
								}
								if(mySites.get(x).isUtilities() == true) {
									noYes = "Yes";
								}else {
									noYes = "N/A";
								}
								
								if(mySites.get(x).getMaxRVLength() == 0) {
									maxRVLength = "N/A";
								}else {
									Integer rvLength =  mySites.get(x).getMaxRVLength();
									maxRVLength = rvLength.toString();
								}
								System.out.printf("%-40s %-15s %-20s %-20s %-20s %-20s $%-20.2f\n", myCampground.getName(), mySites.get(x).getId(),
										mySites.get(x).getMaxOccupancy(), yesNo, maxRVLength,
										noYes, cost);
							}
						}else if (size < 5 && size > 0) {
							for(int x = 0; x < mySites.size(); x++) {
								String yesNo = null;
								String noYes = null;
								String maxRVLength = null;
								if(mySites.get(x).isAccessible() == true) {
									yesNo = "Yes";
								}else {
									yesNo = "No";
								}
								
								if(mySites.get(x).isUtilities() == true) {
									noYes = "Yes";
								}else {
									noYes = "N/A";
								}

								if(mySites.get(x).getMaxRVLength() == 0) {
									maxRVLength = "N/A";
								}else {
									Integer rvLength =  mySites.get(x).getMaxRVLength();
									maxRVLength = rvLength.toString();
								}
								System.out.printf("%-20s %-15s %-20s %-20s %-20s %-20s $%-20.2f\n", myCampground.getName(), mySites.get(x).getId(),
										mySites.get(x).getMaxOccupancy(), yesNo, maxRVLength,
										noYes, cost);
							}
						}else if (size == 0) {
							System.out.println("There are no available sites. Please enter an alternate date range.");
						}
						
					}	
				}else {
					isTrue = true;
				}
				runCreateReservation();
				run();
			} else if(choice.equals(RESERVATION_MENU_RETURN)) {
				isTrue = true;
				runParkInformation();
			}
		}
	}
	
	private void runCreateReservation() {
		String siteNum = null;
		int siteInt = 0;
		boolean isASite = false;
		while (!isASite) {
			try {
				System.out.println("Which site should be reserved (enter 0 to cancel)? ");
				siteNum = input.nextLine();
				siteInt = Integer.parseInt(siteNum);
				if(siteInt == 0) {
					runReservation();
				}
			}catch (NumberFormatException e) {
				
			}
			
			for (int x = 0; x < mySites.size(); x++) {
				if (siteInt == mySites.get(x).getId()) {
					isASite = true;
				}
			}
			if(isASite == false) {
				System.out.println("You did not input a valid site number. Please try again.");
			}
		}
		
		if (!siteNum.contentEquals("0")) {
			System.out.println("What name should the reservation be made under?");
			String name = input.nextLine();
			LocalDate currentDate = LocalDate.now();
			Reservation myReservation = reservationDao.makeReservation(siteInt, name, arrivalLocalDate, departureLocalDate, currentDate);
			if (myReservation.getName() != null) {
				System.out.println("The reservation has been made and the confirmation id is " + myReservation.getId());
			}
		}
	}

	private void displayCampgrounds() {
		campgrounds = campgroundDao.getCampgroundsByParkId(myPark.getId());
		System.out.println("Park Campgrounds");
		System.out.println(myPark.getName() + " National Park Campgrounds");
		System.out.printf("%-6s %-30s %-20s %-20s %-20s", " ", "Name", "Open", "Close", "Daily Fee");
		System.out.println("\n");
		System.out.println("==============================================================================================");
		for (int x = 0; x < campgrounds.size(); x++) {
			String openMonth = null;
			String closeMonth = null;
			
			if (campgrounds.get(x).getOpenFromMM() == 01) {
				openMonth = "January";
			}else if (campgrounds.get(x).getOpenFromMM() == 02) {
				openMonth = "February";
			}else if (campgrounds.get(x).getOpenFromMM() == 03) {
				openMonth = "March";
			}else if (campgrounds.get(x).getOpenFromMM() == 04) {
				openMonth = "April";
			}else if (campgrounds.get(x).getOpenFromMM() == 05) {
				openMonth = "May";
			}else if (campgrounds.get(x).getOpenFromMM() == 06) {
				openMonth = "June";
			}else if (campgrounds.get(x).getOpenFromMM() == 07) {
				openMonth = "July";
			}else if (campgrounds.get(x).getOpenFromMM() == 8) {
				openMonth = "August";
			}else if (campgrounds.get(x).getOpenFromMM() == 9) {
				openMonth = "September";
			}else if (campgrounds.get(x).getOpenFromMM() == 10) {
				openMonth = "October";
			}else if (campgrounds.get(x).getOpenFromMM() == 11) {
				openMonth = "November";
			}else if (campgrounds.get(x).getOpenFromMM() == 12) {
				openMonth = "December";
			}
			
			if (campgrounds.get(x).getOpenToMM() == 01) {
				closeMonth = "January";
			}else if (campgrounds.get(x).getOpenToMM() == 02) {
				closeMonth = "February";
			}else if (campgrounds.get(x).getOpenToMM() == 03) {
				closeMonth = "March";
			}else if (campgrounds.get(x).getOpenToMM() == 04) {
				closeMonth = "April";
			}else if (campgrounds.get(x).getOpenToMM() == 05) {
				closeMonth = "May";
			}else if (campgrounds.get(x).getOpenToMM() == 06) {
				closeMonth = "June";
			}else if (campgrounds.get(x).getOpenToMM() == 07) {
				closeMonth = "July";
			}else if (campgrounds.get(x).getOpenToMM() == 8) {
				closeMonth = "August";
			}else if (campgrounds.get(x).getOpenToMM() == 9) {
				closeMonth = "September";
			}else if (campgrounds.get(x).getOpenToMM() == 10) {
				closeMonth = "October";
			}else if (campgrounds.get(x).getOpenToMM() == 11) {
				closeMonth = "November";
			}else if (campgrounds.get(x).getOpenToMM() == 12) {
				closeMonth = "December";
			}
			
			
			System.out.printf("#%-5s %-30s %-20s %-20s $%-20.2f\n", campgrounds.get(x).getId(), campgrounds.get(x).getName(), 
					openMonth, closeMonth, campgrounds.get(x).getDailyFee());
			
	}
	}
	
}
