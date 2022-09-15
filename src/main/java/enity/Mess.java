package enity;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mess {
	
  private List<String> messes = new LinkedList<>();
  
  public void add(String messes) {
	  this.messes.add(messes);
  }
  
  public boolean contains(String messes) {
	 return this.messes.contains(messes);
  }
}
