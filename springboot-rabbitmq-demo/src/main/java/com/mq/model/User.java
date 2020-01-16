package com.mq.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author halo.l
 * @date 2020-01-13
 */

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1262627851741431084L;

    private String userId;

    private String name;
}
