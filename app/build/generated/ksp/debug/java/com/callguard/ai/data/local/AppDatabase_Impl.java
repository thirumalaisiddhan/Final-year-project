package com.callguard.ai.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CallEventDao _callEventDao;

  private volatile TrustedCallerDao _trustedCallerDao;

  private volatile ServiceContextDao _serviceContextDao;

  private volatile UserFeedbackDao _userFeedbackDao;

  private volatile AppSettingsDao _appSettingsDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `call_events` (`id` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `phoneNumberHash` TEXT NOT NULL, `callerName` TEXT, `timestamp` INTEGER NOT NULL, `timeFormatted` TEXT NOT NULL, `durationSeconds` INTEGER NOT NULL, `direction` TEXT NOT NULL, `calls1Min` INTEGER NOT NULL, `calls5Min` INTEGER NOT NULL, `calls1Hour` INTEGER NOT NULL, `averageDurationSeconds` REAL NOT NULL, `shortCallRatio` REAL NOT NULL, `repeatCallRatio` REAL NOT NULL, `nightCallRatio` REAL NOT NULL, `burstScore` REAL NOT NULL, `riskScore` INTEGER NOT NULL, `riskLevel` TEXT NOT NULL, `predictionReasons` TEXT NOT NULL, `modelVersion` TEXT NOT NULL, `decisionSource` TEXT NOT NULL, `matchedContextOrg` TEXT, `isTrusted` INTEGER NOT NULL, `finalDecision` TEXT NOT NULL, `userFeedback` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trusted_callers` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `phoneNumberOrIdentifier` TEXT NOT NULL, `category` TEXT NOT NULL, `reasonForTrust` TEXT NOT NULL, `approvalDuration` TEXT NOT NULL, `notes` TEXT NOT NULL, `isEnabled` INTEGER NOT NULL, `isOrganization` INTEGER NOT NULL, `approvalStatus` TEXT NOT NULL, `addedDate` TEXT NOT NULL, `expiryDate` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `service_context` (`id` TEXT NOT NULL, `organizationName` TEXT NOT NULL, `category` TEXT NOT NULL, `status` TEXT NOT NULL, `expectedDate` TEXT NOT NULL, `deliveryCallsAllowed` INTEGER NOT NULL, `isEnabled` INTEGER NOT NULL, `notes` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `predictions` (`id` TEXT NOT NULL, `callEventId` TEXT NOT NULL, `riskScore` INTEGER NOT NULL, `riskLevel` TEXT NOT NULL, `reasons` TEXT NOT NULL, `modelVersion` TEXT NOT NULL, `decisionSource` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_feedback` (`id` TEXT NOT NULL, `callEventId` TEXT NOT NULL, `phoneNumber` TEXT NOT NULL, `feedback` TEXT NOT NULL, `trustedAfterFeedback` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `app_settings` (`id` INTEGER NOT NULL, `protectionEnabled` INTEGER NOT NULL, `autoBlockingEnabled` INTEGER NOT NULL, `warnBeforeBlocking` INTEGER NOT NULL, `trustedCallerOverride` INTEGER NOT NULL, `deliveryContextEnabled` INTEGER NOT NULL, `unknownCallerWarnings` INTEGER NOT NULL, `riskThreshold` TEXT NOT NULL, `isDarkMode` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '52b8b6ebf9c271e7b0a24f3ad64e2576')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `call_events`");
        db.execSQL("DROP TABLE IF EXISTS `trusted_callers`");
        db.execSQL("DROP TABLE IF EXISTS `service_context`");
        db.execSQL("DROP TABLE IF EXISTS `predictions`");
        db.execSQL("DROP TABLE IF EXISTS `user_feedback`");
        db.execSQL("DROP TABLE IF EXISTS `app_settings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCallEvents = new HashMap<String, TableInfo.Column>(25);
        _columnsCallEvents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("phoneNumberHash", new TableInfo.Column("phoneNumberHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("callerName", new TableInfo.Column("callerName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("timeFormatted", new TableInfo.Column("timeFormatted", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("durationSeconds", new TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("direction", new TableInfo.Column("direction", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("calls1Min", new TableInfo.Column("calls1Min", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("calls5Min", new TableInfo.Column("calls5Min", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("calls1Hour", new TableInfo.Column("calls1Hour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("averageDurationSeconds", new TableInfo.Column("averageDurationSeconds", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("shortCallRatio", new TableInfo.Column("shortCallRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("repeatCallRatio", new TableInfo.Column("repeatCallRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("nightCallRatio", new TableInfo.Column("nightCallRatio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("burstScore", new TableInfo.Column("burstScore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("riskScore", new TableInfo.Column("riskScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("riskLevel", new TableInfo.Column("riskLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("predictionReasons", new TableInfo.Column("predictionReasons", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("modelVersion", new TableInfo.Column("modelVersion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("decisionSource", new TableInfo.Column("decisionSource", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("matchedContextOrg", new TableInfo.Column("matchedContextOrg", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("isTrusted", new TableInfo.Column("isTrusted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("finalDecision", new TableInfo.Column("finalDecision", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallEvents.put("userFeedback", new TableInfo.Column("userFeedback", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCallEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCallEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCallEvents = new TableInfo("call_events", _columnsCallEvents, _foreignKeysCallEvents, _indicesCallEvents);
        final TableInfo _existingCallEvents = TableInfo.read(db, "call_events");
        if (!_infoCallEvents.equals(_existingCallEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "call_events(com.callguard.ai.data.local.CallEventEntity).\n"
                  + " Expected:\n" + _infoCallEvents + "\n"
                  + " Found:\n" + _existingCallEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsTrustedCallers = new HashMap<String, TableInfo.Column>(12);
        _columnsTrustedCallers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("phoneNumberOrIdentifier", new TableInfo.Column("phoneNumberOrIdentifier", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("reasonForTrust", new TableInfo.Column("reasonForTrust", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("approvalDuration", new TableInfo.Column("approvalDuration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("isOrganization", new TableInfo.Column("isOrganization", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("approvalStatus", new TableInfo.Column("approvalStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("addedDate", new TableInfo.Column("addedDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrustedCallers.put("expiryDate", new TableInfo.Column("expiryDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrustedCallers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTrustedCallers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTrustedCallers = new TableInfo("trusted_callers", _columnsTrustedCallers, _foreignKeysTrustedCallers, _indicesTrustedCallers);
        final TableInfo _existingTrustedCallers = TableInfo.read(db, "trusted_callers");
        if (!_infoTrustedCallers.equals(_existingTrustedCallers)) {
          return new RoomOpenHelper.ValidationResult(false, "trusted_callers(com.callguard.ai.data.local.TrustedCallerEntity).\n"
                  + " Expected:\n" + _infoTrustedCallers + "\n"
                  + " Found:\n" + _existingTrustedCallers);
        }
        final HashMap<String, TableInfo.Column> _columnsServiceContext = new HashMap<String, TableInfo.Column>(8);
        _columnsServiceContext.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("organizationName", new TableInfo.Column("organizationName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("expectedDate", new TableInfo.Column("expectedDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("deliveryCallsAllowed", new TableInfo.Column("deliveryCallsAllowed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceContext.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysServiceContext = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesServiceContext = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoServiceContext = new TableInfo("service_context", _columnsServiceContext, _foreignKeysServiceContext, _indicesServiceContext);
        final TableInfo _existingServiceContext = TableInfo.read(db, "service_context");
        if (!_infoServiceContext.equals(_existingServiceContext)) {
          return new RoomOpenHelper.ValidationResult(false, "service_context(com.callguard.ai.data.local.ServiceContextEntity).\n"
                  + " Expected:\n" + _infoServiceContext + "\n"
                  + " Found:\n" + _existingServiceContext);
        }
        final HashMap<String, TableInfo.Column> _columnsPredictions = new HashMap<String, TableInfo.Column>(8);
        _columnsPredictions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("callEventId", new TableInfo.Column("callEventId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("riskScore", new TableInfo.Column("riskScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("riskLevel", new TableInfo.Column("riskLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("reasons", new TableInfo.Column("reasons", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("modelVersion", new TableInfo.Column("modelVersion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("decisionSource", new TableInfo.Column("decisionSource", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPredictions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPredictions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPredictions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPredictions = new TableInfo("predictions", _columnsPredictions, _foreignKeysPredictions, _indicesPredictions);
        final TableInfo _existingPredictions = TableInfo.read(db, "predictions");
        if (!_infoPredictions.equals(_existingPredictions)) {
          return new RoomOpenHelper.ValidationResult(false, "predictions(com.callguard.ai.data.local.SpamPredictionEntity).\n"
                  + " Expected:\n" + _infoPredictions + "\n"
                  + " Found:\n" + _existingPredictions);
        }
        final HashMap<String, TableInfo.Column> _columnsUserFeedback = new HashMap<String, TableInfo.Column>(6);
        _columnsUserFeedback.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserFeedback.put("callEventId", new TableInfo.Column("callEventId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserFeedback.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserFeedback.put("feedback", new TableInfo.Column("feedback", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserFeedback.put("trustedAfterFeedback", new TableInfo.Column("trustedAfterFeedback", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserFeedback.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserFeedback = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserFeedback = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserFeedback = new TableInfo("user_feedback", _columnsUserFeedback, _foreignKeysUserFeedback, _indicesUserFeedback);
        final TableInfo _existingUserFeedback = TableInfo.read(db, "user_feedback");
        if (!_infoUserFeedback.equals(_existingUserFeedback)) {
          return new RoomOpenHelper.ValidationResult(false, "user_feedback(com.callguard.ai.data.local.UserFeedbackEntity).\n"
                  + " Expected:\n" + _infoUserFeedback + "\n"
                  + " Found:\n" + _existingUserFeedback);
        }
        final HashMap<String, TableInfo.Column> _columnsAppSettings = new HashMap<String, TableInfo.Column>(9);
        _columnsAppSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("protectionEnabled", new TableInfo.Column("protectionEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("autoBlockingEnabled", new TableInfo.Column("autoBlockingEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("warnBeforeBlocking", new TableInfo.Column("warnBeforeBlocking", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("trustedCallerOverride", new TableInfo.Column("trustedCallerOverride", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("deliveryContextEnabled", new TableInfo.Column("deliveryContextEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("unknownCallerWarnings", new TableInfo.Column("unknownCallerWarnings", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("riskThreshold", new TableInfo.Column("riskThreshold", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAppSettings.put("isDarkMode", new TableInfo.Column("isDarkMode", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAppSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAppSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAppSettings = new TableInfo("app_settings", _columnsAppSettings, _foreignKeysAppSettings, _indicesAppSettings);
        final TableInfo _existingAppSettings = TableInfo.read(db, "app_settings");
        if (!_infoAppSettings.equals(_existingAppSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "app_settings(com.callguard.ai.data.local.AppSettingsEntity).\n"
                  + " Expected:\n" + _infoAppSettings + "\n"
                  + " Found:\n" + _existingAppSettings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "52b8b6ebf9c271e7b0a24f3ad64e2576", "13154b15f973b87209d5371e7f437fab");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "call_events","trusted_callers","service_context","predictions","user_feedback","app_settings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `call_events`");
      _db.execSQL("DELETE FROM `trusted_callers`");
      _db.execSQL("DELETE FROM `service_context`");
      _db.execSQL("DELETE FROM `predictions`");
      _db.execSQL("DELETE FROM `user_feedback`");
      _db.execSQL("DELETE FROM `app_settings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CallEventDao.class, CallEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TrustedCallerDao.class, TrustedCallerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ServiceContextDao.class, ServiceContextDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserFeedbackDao.class, UserFeedbackDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AppSettingsDao.class, AppSettingsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CallEventDao callEventDao() {
    if (_callEventDao != null) {
      return _callEventDao;
    } else {
      synchronized(this) {
        if(_callEventDao == null) {
          _callEventDao = new CallEventDao_Impl(this);
        }
        return _callEventDao;
      }
    }
  }

  @Override
  public TrustedCallerDao trustedCallerDao() {
    if (_trustedCallerDao != null) {
      return _trustedCallerDao;
    } else {
      synchronized(this) {
        if(_trustedCallerDao == null) {
          _trustedCallerDao = new TrustedCallerDao_Impl(this);
        }
        return _trustedCallerDao;
      }
    }
  }

  @Override
  public ServiceContextDao serviceContextDao() {
    if (_serviceContextDao != null) {
      return _serviceContextDao;
    } else {
      synchronized(this) {
        if(_serviceContextDao == null) {
          _serviceContextDao = new ServiceContextDao_Impl(this);
        }
        return _serviceContextDao;
      }
    }
  }

  @Override
  public UserFeedbackDao userFeedbackDao() {
    if (_userFeedbackDao != null) {
      return _userFeedbackDao;
    } else {
      synchronized(this) {
        if(_userFeedbackDao == null) {
          _userFeedbackDao = new UserFeedbackDao_Impl(this);
        }
        return _userFeedbackDao;
      }
    }
  }

  @Override
  public AppSettingsDao appSettingsDao() {
    if (_appSettingsDao != null) {
      return _appSettingsDao;
    } else {
      synchronized(this) {
        if(_appSettingsDao == null) {
          _appSettingsDao = new AppSettingsDao_Impl(this);
        }
        return _appSettingsDao;
      }
    }
  }
}
