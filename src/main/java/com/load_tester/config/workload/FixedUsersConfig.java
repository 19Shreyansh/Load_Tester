package com.load_tester.config.workload;

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
