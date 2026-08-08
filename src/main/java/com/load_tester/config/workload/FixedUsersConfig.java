package com.load_tester.config.workload;

import com.load_tester.engine.model.FixedUsersModel;

public class FixedUsersConfig implements WorkloadConfig{
    private final int users;
    private final int durationSeconds;

    public FixedUsersConfig(int users, int durationSeconds){
        this.users=users;
        this.durationSeconds=durationSeconds;
    }
    public int getUsers(){
        return users;
    }
    public int getDurationSeconds(){
        return durationSeconds;
    }
}
