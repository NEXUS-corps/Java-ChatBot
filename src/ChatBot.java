import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.lang.System;


public class ChatBot {
    static HashMap<String, String> responses = new HashMap<>();
    public static void loadResponses(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("responses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=",2);
                if (parts.length >= 2) {
                    responses.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("error reading response.");
        }
    }

    static Random random = new Random();

    static String[] failureResponse = {
            "I'm not sure I understand.",
            "Can you rephrase that?",
            "Sorry, I don’t know how to respond to that.",
            "I'm still learning. Try asking something else."
    };

    public static String defaultresponse() {
        int n = random.nextInt(failureResponse.length);
        return failureResponse[n];
    }


    static String[] quotes = {
            "The best way to get started is to quit talking and begin doing. – Walt Disney",
            "Don’t let yesterday take up too much of today. – Will Rogers",
            "It’s not whether you get knocked down, it’s whether you get up. – Vince Lombardi",
            "If you are working on something exciting, it will keep you motivated. – Steve Jobs",
            "Success is not in what you have, but who you are. – Bo Bennett",
            "The harder you work for something, the greater you’ll feel when you achieve it.",
            "Dream bigger. Do bigger.",
            "Don’t watch the clock; do what it does. Keep going. – Sam Levenson",
            "Great things never come from comfort zones.",
            "Push yourself, because no one else is going to do it for you.",
            "Sometimes later becomes never. Do it now.",
            "Little things make big days.",
            "It’s going to be hard, but hard does not mean impossible.",
            "Don’t wait for opportunity. Create it.",
            "Success doesn’t just find you. You have to go out and get it.",
            "The only limit to our realization of tomorrow will be our doubts of today. – Franklin D. Roosevelt",
            "You are never too old to set another goal or to dream a new dream. – C.S. Lewis",
            "Everything you’ve ever wanted is on the other side of fear. – George Addair",
            "Opportunities don't happen, you create them. – Chris Grosser",
            "Do what you can with all you have, wherever you are. – Theodore Roosevelt",
            "What you get by achieving your goals is not as important as what you become by achieving your goals. – Zig Ziglar",
            "In the middle of every difficulty lies opportunity. – Albert Einstein",
            "Happiness is not something ready made. It comes from your own actions. – Dalai Lama",
            "Act as if what you do makes a difference. It does. – William James",
            "The only way to do great work is to love what you do. – Steve Jobs",
            "You don’t have to be great to start, but you have to start to be great. – Zig Ziglar",
            "Believe you can and you’re halfway there. – Theodore Roosevelt",
            "The future depends on what you do today. – Mahatma Gandhi",
            "Difficult roads often lead to beautiful destinations.",
            "Mistakes are proof that you are trying.",
            "You are capable of amazing things.",
            "Stay positive, work hard, make it happen.",
            "Your limitation—it’s only your imagination.",
            "Sometimes we’re tested not to show our weaknesses, but to discover our strengths.",
            "Doubt kills more dreams than failure ever will. – Suzy Kassem",
            "The secret of getting ahead is getting started. – Mark Twain",
            "Don’t be pushed around by the fears in your mind. Be led by the dreams in your heart. – Roy T. Bennett",
            "If you want to achieve greatness stop asking for permission. – Anonymous",
            "The only person you are destined to become is the person you decide to be. – Ralph Waldo Emerson",
            "Start where you are. Use what you have. Do what you can. – Arthur Ashe",
            "Perseverance is not a long race; it is many short races one after the other. – Walter Elliot"
    };

    public static void timeBasedGreeting() {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        if (hour >= 5 && hour < 12) {
            System.out.println("Good Morning");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon");
        } else {
            System.out.println("Good Evening");
        }
    }

    public static String getcurrentTime() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        return LocalTime.now().format(timeFormatter).toString();
    }

    public static String getCurrentDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().format(dateFormatter).toString();
    }

    public static void getCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        System.out.println(dayOfWeek);
    }

    public static String inputtrim(String abc) {
        return abc.replaceAll("[,\\s?.]", "");
    }

    public static boolean isPalindrome(String text) {
        String cleaned = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        loadResponses("responses.txt");
        timeBasedGreeting();
        System.out.println("Hi how can I help you");
        System.out.println("Type exit to quit");
        System.out.println("Type function to know what I can do");
        System.out.println("Type 'how' with function name to know how to activate it.");
        OMDBApi m1=new OMDBApi();
        weatherapi w1= new weatherapi();

        while (true) {
            System.out.print("You: ");
            input = scanner.nextLine().toLowerCase();
            input=inputtrim(input);
            if (input.equals("exit")) {
                System.out.println("Chatbot: " + responses.get("bye"));
                break;
            }
            else if(input.equals("functions")) {
                System.out.println("Chatbot:"+ responses.get(input));
            }else if (responses.containsKey(input)) {
                System.out.println("Chatbot: " + responses.get(input));
            } else if (input.equals("time")) {
                System.out.println("Chatbot: current time is " + getcurrentTime());
            } else if (input.equals("date")) {
                System.out.println("Chatbot: Today's date is: " + getCurrentDate());
            } else if (input.equals("day")) {
                System.out.print("Chatbot: Today is ");
                getCurrentDay();
            } else if(input.equals("movie")){
                System.out.println("Chatbot: Type name of the movie");
                String mname=scanner.nextLine();
                String movieInfo=m1.getMovieInfo(mname);
                System.out.println("Chatbot: " + movieInfo);
            } else if(input.equals("weather")){
                System.out.println("Chatbot: Type name of the city");
                String mname=scanner.nextLine();
                String weatherInfo=w1.getWeatherInfo(mname);
                System.out.println("Chatbot: " + weatherInfo);
            } else if (input.equals("systeminfo")) {
                System.out.println("OS: " + System.getProperty("os.name"));
                System.out.println("Java version: " + System.getProperty("java.version"));
            }else if (input.startsWith("palindrome")) {
                // Extract the word or phrase after the command
                String toCheck = input.substring("palindrome".length()).trim();
                if (toCheck.isEmpty()) {
                    System.out.println("Chatbot: Please provide a word or phrase to check.");
                } else {
                    if (isPalindrome(toCheck)) {
                        System.out.println("Chatbot: Yes, '" + toCheck + "' is a palindrome!");
                    } else {
                        System.out.println("Chatbot: No, '" + toCheck + "' is not a palindrome.");
                    }
                }
            }else if (input.equals("generatequote")) {
                int idx = random.nextInt(quotes.length);
                System.out.println("Chatbot: " + quotes[idx]);
            } else {
                System.out.println("Chatbot: " + defaultresponse());
            }
        }
        scanner.close();
    }

}
class OMDBApi {
    private final String apiKey = "b46e43ca"; // Just the key

    public String getMovieInfo(String name) {
        try {
            String encodedName = URLEncoder.encode(name, "UTF-8");
            String urlString = "http://www.omdbapi.com/?t=" + encodedName + "&apikey=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            return content.toString();
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}

class weatherapi{
    public String cityName;
    private final String apiKey="ea581a137701df1e4b32c6a4de8b9da0";
    public String getWeatherInfo(String cityName){
        this.cityName= cityName;
        try {
            String encodedCity = URLEncoder.encode(cityName, "UTF-8");
            String urlString = "http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + encodedCity;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            return content.toString();
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
