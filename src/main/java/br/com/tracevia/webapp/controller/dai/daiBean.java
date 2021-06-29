package br.com.tracevia.webapp.controller.dai;

import java.time.LocalDateTime;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.tracevia.webapp.dao.occ.OccurrencesDAO;
import br.com.tracevia.webapp.model.dai.DAI;
import br.com.tracevia.webapp.model.global.Equipments;

@ManagedBean(name="daiBean")
@ViewScoped
public class DaiBean {
	public List<Traffic> traffics;
	public Traffic traffic;
	
	public Traffic getTraffic() {
		return traffic;
	}

	public void setTraffic(int idx) {
		this.traffic = traffics.get(idx);
	}

	public List<Traffic> getTraffics() {
		return traffics;
	}

	@PostConstruct
	public void initalize(){
		SimpleDateFormat formattter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		try {
			getAllFile(formattter.format(date));
			setTraffic(0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static class Traffic {
		int id;
		String incident;
		String date;
		String channel;
		String lane;
		String direction;
		String hour;
		String plate;
		Path file;

		Traffic(Path path, int idx) throws IOException, ParseException {
			file = path;
			
			String[] pathS = path.toString().split("\\\\");
			String[] info = pathS[pathS.length-1].split("\\.")[0].split("_");
			Date date_new = new SimpleDateFormat("yyyyMMdd").parse(info[4]);
			Date hour_new = new SimpleDateFormat("hhmmssSSS").parse(info[5]);
			SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat hour_formatter = new SimpleDateFormat("hh:mm:ss");
			
			id = idx;
			incident = info[0];
			channel = info[1];
			lane = info[2];
			direction = info[3];
			date = date_formatter.format(date_new);
			hour = hour_formatter.format(hour_new);
			plate = info[6];
		}
		
		public int getId() {
			return id;
		}
		
		public String getLane() {
			return lane;
		}

		public String getDirection() {
			return direction;
		}
		
		public String getChannel() {
			return channel;
		}
		
		public String getIncident() {
			return incident;
		}

		public String getDate() {
			return date;
		}

		public String getHour() {
			return hour;
		}

		public String getPlate() {
			return plate;
		}

		public String getPath() throws IOException {
			return Base64.getEncoder().encodeToString(Files.readAllBytes(file));
		}

		static List<Traffic> all_traffic(List<Path> list, int idx) throws IOException, ParseException {
			List<Traffic> new_list = new ArrayList<>();
			for (final Path path : list) {
				new_list.add(new Traffic(path, idx));
				idx++;
			}
			
			return new_list;
		}
	}
	
	public void getAllFile(String date) throws IOException, ParseException {
		List<Path> list = getAllFolders(date);
		List<Traffic> new_list = new ArrayList<>();
		for (final Path path : list) {
			List<Path> files = listFiles(path.toString());
			new_list.addAll(Traffic.all_traffic(files, new_list.size()));
		}
		
		traffics = new_list;
	}

	public List<Path> getAllFolders(String date) throws IOException {
		String folder = "E:\\Camaras DAI\\";
		String path = folder + "10.14.120.186\\Traffic Incident\\" + date;
		List<Path> allPath = listFiles(path);
		allPath.addAll(listFiles(folder + "10.14.120.187\\Traffic Incident\\" + date));
		allPath.addAll(listFiles(folder + "10.14.120.188\\Traffic Incident\\" + date));
		
		return allPath;
	}

	public static List<Path> listFiles(String pathS) throws IOException {

        List<Path> result;
        Path path = Paths.get(pathS); 
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;

    }
}
