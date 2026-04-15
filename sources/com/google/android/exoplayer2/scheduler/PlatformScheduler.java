package com.google.android.exoplayer2.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

@RequiresApi(21)
public final class PlatformScheduler implements Scheduler {
    public static final int d = ((Util.a >= 26 ? 16 : 0) | 15);
    public final int a;
    public final ComponentName b;
    public final JobScheduler c;

    public static final class PlatformSchedulerService extends JobService {
        public boolean onStartJob(JobParameters jobParameters) {
            PersistableBundle extras = jobParameters.getExtras();
            int notMetRequirements = new Requirements(extras.getInt("requirements")).getNotMetRequirements(this);
            if (notMetRequirements == 0) {
                Util.startForegroundService(this, new Intent((String) Assertions.checkNotNull(extras.getString("service_action"))).setPackage((String) Assertions.checkNotNull(extras.getString("service_package"))));
                return false;
            }
            y2.t(33, "Requirements not met: ", notMetRequirements, "PlatformScheduler");
            jobFinished(jobParameters, true);
            return false;
        }

        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    @RequiresPermission("android.permission.RECEIVE_BOOT_COMPLETED")
    public PlatformScheduler(Context context, int i) {
        Context applicationContext = context.getApplicationContext();
        this.a = i;
        this.b = new ComponentName(applicationContext, PlatformSchedulerService.class);
        this.c = (JobScheduler) Assertions.checkNotNull((JobScheduler) applicationContext.getSystemService("jobscheduler"));
    }

    public boolean cancel() {
        this.c.cancel(this.a);
        return true;
    }

    public Requirements getSupportedRequirements(Requirements requirements) {
        return requirements.filterRequirements(d);
    }

    public boolean schedule(Requirements requirements, String str, String str2) {
        Requirements filterRequirements = requirements.filterRequirements(d);
        if (!filterRequirements.equals(requirements)) {
            y2.t(46, "Ignoring unsupported requirements: ", filterRequirements.getRequirements() ^ requirements.getRequirements(), "PlatformScheduler");
        }
        JobInfo.Builder builder = new JobInfo.Builder(this.a, this.b);
        if (requirements.isUnmeteredNetworkRequired()) {
            builder.setRequiredNetworkType(2);
        } else if (requirements.isNetworkRequired()) {
            builder.setRequiredNetworkType(1);
        }
        builder.setRequiresDeviceIdle(requirements.isIdleRequired());
        builder.setRequiresCharging(requirements.isChargingRequired());
        if (Util.a >= 26 && requirements.isStorageNotLowRequired()) {
            builder.setRequiresStorageNotLow(true);
        }
        builder.setPersisted(true);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("service_action", str2);
        persistableBundle.putString("service_package", str);
        persistableBundle.putInt("requirements", requirements.getRequirements());
        builder.setExtras(persistableBundle);
        if (this.c.schedule(builder.build()) == 1) {
            return true;
        }
        return false;
    }
}
