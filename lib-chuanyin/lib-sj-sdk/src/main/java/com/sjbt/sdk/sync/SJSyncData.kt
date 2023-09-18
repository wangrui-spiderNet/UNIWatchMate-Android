package com.sjbt.sdk.sync

import com.base.sdk.entity.data.*
import com.base.sdk.entity.settings.WmDeviceInfo
import com.base.sdk.`interface`.sync.AbSyncData
import com.base.sdk.`interface`.sync.AbWmSyncs

class SJSyncData : AbWmSyncs(){
     override var syncStepData: AbSyncData<WmStepData>?
          get() = SyncStepData()
          set(value) {}
     override var syncOxygenData: AbSyncData<WmOxygenData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncCaloriesData: AbSyncData<WmCaloriesData>?
          get() =  TODO("Not yet implemented")
          set(value) {}
     override var syncSleepData: AbSyncData<WmSleepData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncRealtimeRateData: AbSyncData<WmRealtimeRateData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncAvgHeartRateData: AbSyncData<WmHeartRateData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncDistanceData: AbSyncData<WmDistanceData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncActivityData: AbSyncData<WmActivityData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncSportSummaryData: AbSyncData<WmSportSummaryData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncDeviceInfoData: AbSyncData<WmDeviceInfo>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncTodayInfoData: AbSyncData<WmTodayTotalData>?
          get() = TODO("Not yet implemented")
          set(value) {}
}