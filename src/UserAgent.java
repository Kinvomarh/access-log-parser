import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Класс для выделения USER Agent
public class UserAgent {

    private String userAgent;
    private final String bot;

    public boolean isBot = false;
    private final String system;
    private final String browser;




    public UserAgent(String userAgent){
        this.userAgent = userAgent;
        if(userAgent!=null) {
            this.bot = setBot(userAgent);
            this.system = parsSystem(userAgent);
            this.browser = parsbrowser(userAgent);
        }else {
            this.bot = null;
            this.system = null;
            this.browser = null;
        }
        if(bot!=null) this.isBot=true;
    }

    public String getBot() {
        return bot;
    }

    public String getSystem() {
        return system;
    }

    public String getBrowser() {
        return browser;
    }

    //Метод парсинга подстроки с User Agent в результате в строку bot записывается второй фрагмент подстроки (compatible ;;)
    public String setBot(String userAgent){
        String pars = userAgent;
        String res = null;

        if(pars.indexOf("(compatible")!=-1) {
            pars = pars.substring(pars.indexOf("(compatible") + 1);
            if(pars.indexOf(")")!=-1) {
                pars = pars.substring(0, pars.indexOf(")"));
                if(pars. indexOf(";")!=-1){
                String[] bot = pars.split(";");
                for (int i = 0; i < bot.length; i++) {
                    bot[i] = bot[i].trim();
                }
                if (bot.length>1 && (bot[1].indexOf("bot") != -1) || (bot[1].indexOf("Bot") != -1) ) {
                    res = bot[1];
                    if (res.indexOf("/") != -1) {
                        return bot[1].substring(0, bot[1].indexOf("/"));
                    }
                }
                }
            }
        }
            return res;
    }
    // метод выделения системы
    public String parsSystem (String userAgent){
        String pars = userAgent;
        String res = "";
        if(pars.contains("KHTML, like Gecko")){
            pars = pars.substring(0,pars.indexOf("KHTML, like Gecko")-1);
        }
        if(pars.contains("compatible")){
            pars = pars.substring(0,pars.indexOf("compatible")-1);
        }
        pars.trim();
        if(pars.indexOf("(")!=-1 && pars.indexOf(")")!=-1) {
            pars = pars.substring(pars.indexOf("(") + 1, pars.indexOf(")"));
            String[] parsingSystem;
            if (!pars.startsWith("compatible")) {
                parsingSystem = pars.split(";");
                res = parsingSystem[0];
            }
        }
        return res;
    }

    // метод выделения браузера
    public String parsbrowser(String userAgent){

        String sub = userAgent;
        String res = null;


        if(sub.indexOf("Gecko")!=-1) {
            sub = sub.substring(sub.indexOf("Gecko") + 6);


            if (sub.indexOf("(compatible") != -1) {
                sub = sub.substring(1, sub.indexOf("(compatible"));
            }
            sub = sub.trim();

            if(sub.indexOf("\"")!=-1){
                sub= sub.substring(0, sub.indexOf("\""));
            }
            String[] pars = sub.split(" ");

            for (int i = pars.length-1; i>=0; i--){

                if(!isNumber(pars[i])){

                    if (pars[i].indexOf("/")!=-1){
                        res = pars[i].substring(0, pars[i].indexOf("/"));
                        break;
                    } else {
                        res = pars[i];
                        break;
                    }
                }
            }
        }
    return res;
    }

    @Override
    public String toString() {
        return "UserAgent: " + bot;
    }

    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isBot() {
        return isBot;
    }
}


