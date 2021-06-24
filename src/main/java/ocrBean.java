
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="ocrBean")
@ViewScoped
public class ocrBean {
	private String[] typeInfo;
	public String[] getTypeInfo() {
		return typeInfo;
	}
	public void setTypeInfo(String[] typeInfo) {
		this.typeInfo = typeInfo;
	}
	@PostConstruct
	public void initialize() {
		System.out.println("chegamos aqui");
		typeInfo = new String[3];
		typeInfo [0]= "Id";
		typeInfo [1]= "Data|Hora";
		typeInfo [2]= "Câmera";
		typeInfo [3]= "Placa";
	}
	
}