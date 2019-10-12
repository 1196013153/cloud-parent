package com.yqs.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yqs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -3928832861296252415L;
    private int id;
    private String name;
    private Date time;
    private String password;
}
