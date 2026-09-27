package com.callguard.ai.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.callguard.ai.data.model.RiskThreshold;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppSettingsDao_Impl implements AppSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AppSettingsEntity> __insertionAdapterOfAppSettingsEntity;

  private final Converters __converters = new Converters();

  public AppSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAppSettingsEntity = new EntityInsertionAdapter<AppSettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `app_settings` (`id`,`protectionEnabled`,`autoBlockingEnabled`,`warnBeforeBlocking`,`trustedCallerOverride`,`deliveryContextEnabled`,`unknownCallerWarnings`,`riskThreshold`,`isDarkMode`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AppSettingsEntity entity) {
        statement.bindLong(1, entity.getId());
        final int _tmp = entity.getProtectionEnabled() ? 1 : 0;
        statement.bindLong(2, _tmp);
        final int _tmp_1 = entity.getAutoBlockingEnabled() ? 1 : 0;
        statement.bindLong(3, _tmp_1);
        final int _tmp_2 = entity.getWarnBeforeBlocking() ? 1 : 0;
        statement.bindLong(4, _tmp_2);
        final int _tmp_3 = entity.getTrustedCallerOverride() ? 1 : 0;
        statement.bindLong(5, _tmp_3);
        final int _tmp_4 = entity.getDeliveryContextEnabled() ? 1 : 0;
        statement.bindLong(6, _tmp_4);
        final int _tmp_5 = entity.getUnknownCallerWarnings() ? 1 : 0;
        statement.bindLong(7, _tmp_5);
        final String _tmp_6 = __converters.fromRiskThreshold(entity.getRiskThreshold());
        statement.bindString(8, _tmp_6);
        final Integer _tmp_7 = entity.isDarkMode() == null ? null : (entity.isDarkMode() ? 1 : 0);
        if (_tmp_7 == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, _tmp_7);
        }
      }
    };
  }

  @Override
  public Object saveSettings(final AppSettingsEntity settings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAppSettingsEntity.insert(settings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<AppSettingsEntity> getSettings() {
    final String _sql = "SELECT * FROM app_settings WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"app_settings"}, new Callable<AppSettingsEntity>() {
      @Override
      @Nullable
      public AppSettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProtectionEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "protectionEnabled");
          final int _cursorIndexOfAutoBlockingEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "autoBlockingEnabled");
          final int _cursorIndexOfWarnBeforeBlocking = CursorUtil.getColumnIndexOrThrow(_cursor, "warnBeforeBlocking");
          final int _cursorIndexOfTrustedCallerOverride = CursorUtil.getColumnIndexOrThrow(_cursor, "trustedCallerOverride");
          final int _cursorIndexOfDeliveryContextEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryContextEnabled");
          final int _cursorIndexOfUnknownCallerWarnings = CursorUtil.getColumnIndexOrThrow(_cursor, "unknownCallerWarnings");
          final int _cursorIndexOfRiskThreshold = CursorUtil.getColumnIndexOrThrow(_cursor, "riskThreshold");
          final int _cursorIndexOfIsDarkMode = CursorUtil.getColumnIndexOrThrow(_cursor, "isDarkMode");
          final AppSettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final boolean _tmpProtectionEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfProtectionEnabled);
            _tmpProtectionEnabled = _tmp != 0;
            final boolean _tmpAutoBlockingEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoBlockingEnabled);
            _tmpAutoBlockingEnabled = _tmp_1 != 0;
            final boolean _tmpWarnBeforeBlocking;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfWarnBeforeBlocking);
            _tmpWarnBeforeBlocking = _tmp_2 != 0;
            final boolean _tmpTrustedCallerOverride;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfTrustedCallerOverride);
            _tmpTrustedCallerOverride = _tmp_3 != 0;
            final boolean _tmpDeliveryContextEnabled;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfDeliveryContextEnabled);
            _tmpDeliveryContextEnabled = _tmp_4 != 0;
            final boolean _tmpUnknownCallerWarnings;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfUnknownCallerWarnings);
            _tmpUnknownCallerWarnings = _tmp_5 != 0;
            final RiskThreshold _tmpRiskThreshold;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfRiskThreshold);
            _tmpRiskThreshold = __converters.toRiskThreshold(_tmp_6);
            final Boolean _tmpIsDarkMode;
            final Integer _tmp_7;
            if (_cursor.isNull(_cursorIndexOfIsDarkMode)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getInt(_cursorIndexOfIsDarkMode);
            }
            _tmpIsDarkMode = _tmp_7 == null ? null : _tmp_7 != 0;
            _result = new AppSettingsEntity(_tmpId,_tmpProtectionEnabled,_tmpAutoBlockingEnabled,_tmpWarnBeforeBlocking,_tmpTrustedCallerOverride,_tmpDeliveryContextEnabled,_tmpUnknownCallerWarnings,_tmpRiskThreshold,_tmpIsDarkMode);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
