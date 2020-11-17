package com.app.storytel.challenge.model;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author samsonfagade
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "application_roles")
@AttributeOverride(name = "id", column = @Column(name = "app_role_id", nullable = false, columnDefinition = "BIGINT"))
public class ApplicationRole extends StorytelModelBase implements Serializable {

    @NotNull
    @Size(max = 25)
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_description")
    private String roleDescription;
}
