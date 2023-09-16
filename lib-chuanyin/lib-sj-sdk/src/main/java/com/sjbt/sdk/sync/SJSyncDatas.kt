package com.sjbt.sdk.sync

import com.base.sdk.entity.data.*
import com.base.sdk.`interface`.sync.AbSyncData
import com.base.sdk.`interface`.sync.AbWmSyncs

class SJSyncDatas : AbWmSyncs(){
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
     override var syncAvgHeartRateData: AbSyncData<WmAvgHeartRateData>?
          get() = TODO("Not yet implemented")
          set(value) {}
     override var syncDistanceData: AbSyncData<WmDistanceData>?
          get() = TODO("Not yet implemented")
          set(value) {}
}