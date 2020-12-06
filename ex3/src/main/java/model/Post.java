package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Post {
    private Long id;
    private String title;
    private boolean hidden;
    private List<String> tags;

    public boolean isHidden() {
        return hidden;
    }
}
