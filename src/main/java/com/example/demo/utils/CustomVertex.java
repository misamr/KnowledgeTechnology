package java.com.example.demo.utils;

/**
 * this class is used for loading the questions
 */
public class CustomVertex {
    private String id;
    private String label;
    private String display;
    public CustomVertex(String id)
    {
        super();
        this.id = id;
        this.label = null;
        this.display = null;
    }

    public CustomVertex(String id, String label, String display) {
        super();
        this.id = id;
        this.label = label;
        this.display = display;
    }
    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }
    public String getLabel() {

        return label;
    }
    public void setLabel(String label) {

        this.label = label;
    }
    public String getDisplay() {

        return display;
    }
    public void setDisplay(String display) {

        this.display = display;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((display == null) ? 0 : display.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomVertex other = (CustomVertex) obj;
        if (display == null) {
            if (other.display != null)
                return false;
        } else if (!display.equals(other.display))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (label == null) {
            if (other.label != null)
                return false;
        } else if (!label.equals(other.label))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "CustomVertex [id=" + id + ", label=" + label + ", display=" + display + "]";
    }
}
