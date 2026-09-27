package com.callguard.ai.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.callguard.ai.data.model.CallDecision;
import com.callguard.ai.data.model.CallDirection;
import com.callguard.ai.data.model.RiskLevel;
import com.callguard.ai.data.model.UserFeedbackType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CallEventDao_Impl implements CallEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CallEventEntity> __insertionAdapterOfCallEventEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<CallEventEntity> __updateAdapterOfCallEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public CallEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCallEventEntity = new EntityInsertionAdapter<CallEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `call_events` (`id`,`phoneNumber`,`phoneNumberHash`,`callerName`,`timestamp`,`timeFormatted`,`durationSeconds`,`direction`,`calls1Min`,`calls5Min`,`calls1Hour`,`averageDurationSeconds`,`shortCallRatio`,`repeatCallRatio`,`nightCallRatio`,`burstScore`,`riskScore`,`riskLevel`,`predictionReasons`,`modelVersion`,`decisionSource`,`matchedContextOrg`,`isTrusted`,`finalDecision`,`userFeedback`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallEventEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        statement.bindString(3, entity.getPhoneNumberHash());
        if (entity.getCallerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCallerName());
        }
        statement.bindLong(5, entity.getTimestamp());
        statement.bindString(6, entity.getTimeFormatted());
        statement.bindLong(7, entity.getDurationSeconds());
        final String _tmp = __converters.fromCallDirection(entity.getDirection());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getCalls1Min());
        statement.bindLong(10, entity.getCalls5Min());
        statement.bindLong(11, entity.getCalls1Hour());
        statement.bindDouble(12, entity.getAverageDurationSeconds());
        statement.bindDouble(13, entity.getShortCallRatio());
        statement.bindDouble(14, entity.getRepeatCallRatio());
        statement.bindDouble(15, entity.getNightCallRatio());
        statement.bindDouble(16, entity.getBurstScore());
        statement.bindLong(17, entity.getRiskScore());
        final String _tmp_1 = __converters.fromRiskLevel(entity.getRiskLevel());
        statement.bindString(18, _tmp_1);
        final String _tmp_2 = __converters.fromStringList(entity.getPredictionReasons());
        statement.bindString(19, _tmp_2);
        statement.bindString(20, entity.getModelVersion());
        statement.bindString(21, entity.getDecisionSource());
        if (entity.getMatchedContextOrg() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getMatchedContextOrg());
        }
        final int _tmp_3 = entity.isTrusted() ? 1 : 0;
        statement.bindLong(23, _tmp_3);
        final String _tmp_4 = __converters.fromCallDecision(entity.getFinalDecision());
        statement.bindString(24, _tmp_4);
        final String _tmp_5 = __converters.fromUserFeedbackType(entity.getUserFeedback());
        if (_tmp_5 == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, _tmp_5);
        }
      }
    };
    this.__updateAdapterOfCallEventEntity = new EntityDeletionOrUpdateAdapter<CallEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `call_events` SET `id` = ?,`phoneNumber` = ?,`phoneNumberHash` = ?,`callerName` = ?,`timestamp` = ?,`timeFormatted` = ?,`durationSeconds` = ?,`direction` = ?,`calls1Min` = ?,`calls5Min` = ?,`calls1Hour` = ?,`averageDurationSeconds` = ?,`shortCallRatio` = ?,`repeatCallRatio` = ?,`nightCallRatio` = ?,`burstScore` = ?,`riskScore` = ?,`riskLevel` = ?,`predictionReasons` = ?,`modelVersion` = ?,`decisionSource` = ?,`matchedContextOrg` = ?,`isTrusted` = ?,`finalDecision` = ?,`userFeedback` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallEventEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        statement.bindString(3, entity.getPhoneNumberHash());
        if (entity.getCallerName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCallerName());
        }
        statement.bindLong(5, entity.getTimestamp());
        statement.bindString(6, entity.getTimeFormatted());
        statement.bindLong(7, entity.getDurationSeconds());
        final String _tmp = __converters.fromCallDirection(entity.getDirection());
        statement.bindString(8, _tmp);
        statement.bindLong(9, entity.getCalls1Min());
        statement.bindLong(10, entity.getCalls5Min());
        statement.bindLong(11, entity.getCalls1Hour());
        statement.bindDouble(12, entity.getAverageDurationSeconds());
        statement.bindDouble(13, entity.getShortCallRatio());
        statement.bindDouble(14, entity.getRepeatCallRatio());
        statement.bindDouble(15, entity.getNightCallRatio());
        statement.bindDouble(16, entity.getBurstScore());
        statement.bindLong(17, entity.getRiskScore());
        final String _tmp_1 = __converters.fromRiskLevel(entity.getRiskLevel());
        statement.bindString(18, _tmp_1);
        final String _tmp_2 = __converters.fromStringList(entity.getPredictionReasons());
        statement.bindString(19, _tmp_2);
        statement.bindString(20, entity.getModelVersion());
        statement.bindString(21, entity.getDecisionSource());
        if (entity.getMatchedContextOrg() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getMatchedContextOrg());
        }
        final int _tmp_3 = entity.isTrusted() ? 1 : 0;
        statement.bindLong(23, _tmp_3);
        final String _tmp_4 = __converters.fromCallDecision(entity.getFinalDecision());
        statement.bindString(24, _tmp_4);
        final String _tmp_5 = __converters.fromUserFeedbackType(entity.getUserFeedback());
        if (_tmp_5 == null) {
          statement.bindNull(25);
        } else {
          statement.bindString(25, _tmp_5);
        }
        statement.bindString(26, entity.getId());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM call_events";
        return _query;
      }
    };
  }

  @Override
  public Object insertCall(final CallEventEntity call,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCallEventEntity.insert(call);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<CallEventEntity> calls,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCallEventEntity.insert(calls);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCall(final CallEventEntity call,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCallEventEntity.handle(call);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CallEventEntity>> getAllCalls() {
    final String _sql = "SELECT * FROM call_events ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_events"}, new Callable<List<CallEventEntity>>() {
      @Override
      @NonNull
      public List<CallEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhoneNumberHash = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumberHash");
          final int _cursorIndexOfCallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "callerName");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimeFormatted = CursorUtil.getColumnIndexOrThrow(_cursor, "timeFormatted");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfCalls1Min = CursorUtil.getColumnIndexOrThrow(_cursor, "calls1Min");
          final int _cursorIndexOfCalls5Min = CursorUtil.getColumnIndexOrThrow(_cursor, "calls5Min");
          final int _cursorIndexOfCalls1Hour = CursorUtil.getColumnIndexOrThrow(_cursor, "calls1Hour");
          final int _cursorIndexOfAverageDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "averageDurationSeconds");
          final int _cursorIndexOfShortCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "shortCallRatio");
          final int _cursorIndexOfRepeatCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatCallRatio");
          final int _cursorIndexOfNightCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "nightCallRatio");
          final int _cursorIndexOfBurstScore = CursorUtil.getColumnIndexOrThrow(_cursor, "burstScore");
          final int _cursorIndexOfRiskScore = CursorUtil.getColumnIndexOrThrow(_cursor, "riskScore");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfPredictionReasons = CursorUtil.getColumnIndexOrThrow(_cursor, "predictionReasons");
          final int _cursorIndexOfModelVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "modelVersion");
          final int _cursorIndexOfDecisionSource = CursorUtil.getColumnIndexOrThrow(_cursor, "decisionSource");
          final int _cursorIndexOfMatchedContextOrg = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedContextOrg");
          final int _cursorIndexOfIsTrusted = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrusted");
          final int _cursorIndexOfFinalDecision = CursorUtil.getColumnIndexOrThrow(_cursor, "finalDecision");
          final int _cursorIndexOfUserFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "userFeedback");
          final List<CallEventEntity> _result = new ArrayList<CallEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallEventEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhoneNumberHash;
            _tmpPhoneNumberHash = _cursor.getString(_cursorIndexOfPhoneNumberHash);
            final String _tmpCallerName;
            if (_cursor.isNull(_cursorIndexOfCallerName)) {
              _tmpCallerName = null;
            } else {
              _tmpCallerName = _cursor.getString(_cursorIndexOfCallerName);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpTimeFormatted;
            _tmpTimeFormatted = _cursor.getString(_cursorIndexOfTimeFormatted);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            final CallDirection _tmpDirection;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDirection);
            _tmpDirection = __converters.toCallDirection(_tmp);
            final int _tmpCalls1Min;
            _tmpCalls1Min = _cursor.getInt(_cursorIndexOfCalls1Min);
            final int _tmpCalls5Min;
            _tmpCalls5Min = _cursor.getInt(_cursorIndexOfCalls5Min);
            final int _tmpCalls1Hour;
            _tmpCalls1Hour = _cursor.getInt(_cursorIndexOfCalls1Hour);
            final float _tmpAverageDurationSeconds;
            _tmpAverageDurationSeconds = _cursor.getFloat(_cursorIndexOfAverageDurationSeconds);
            final float _tmpShortCallRatio;
            _tmpShortCallRatio = _cursor.getFloat(_cursorIndexOfShortCallRatio);
            final float _tmpRepeatCallRatio;
            _tmpRepeatCallRatio = _cursor.getFloat(_cursorIndexOfRepeatCallRatio);
            final float _tmpNightCallRatio;
            _tmpNightCallRatio = _cursor.getFloat(_cursorIndexOfNightCallRatio);
            final float _tmpBurstScore;
            _tmpBurstScore = _cursor.getFloat(_cursorIndexOfBurstScore);
            final int _tmpRiskScore;
            _tmpRiskScore = _cursor.getInt(_cursorIndexOfRiskScore);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            _tmpRiskLevel = __converters.toRiskLevel(_tmp_1);
            final List<String> _tmpPredictionReasons;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfPredictionReasons);
            _tmpPredictionReasons = __converters.toStringList(_tmp_2);
            final String _tmpModelVersion;
            _tmpModelVersion = _cursor.getString(_cursorIndexOfModelVersion);
            final String _tmpDecisionSource;
            _tmpDecisionSource = _cursor.getString(_cursorIndexOfDecisionSource);
            final String _tmpMatchedContextOrg;
            if (_cursor.isNull(_cursorIndexOfMatchedContextOrg)) {
              _tmpMatchedContextOrg = null;
            } else {
              _tmpMatchedContextOrg = _cursor.getString(_cursorIndexOfMatchedContextOrg);
            }
            final boolean _tmpIsTrusted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsTrusted);
            _tmpIsTrusted = _tmp_3 != 0;
            final CallDecision _tmpFinalDecision;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFinalDecision);
            _tmpFinalDecision = __converters.toCallDecision(_tmp_4);
            final UserFeedbackType _tmpUserFeedback;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUserFeedback)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfUserFeedback);
            }
            _tmpUserFeedback = __converters.toUserFeedbackType(_tmp_5);
            _item = new CallEventEntity(_tmpId,_tmpPhoneNumber,_tmpPhoneNumberHash,_tmpCallerName,_tmpTimestamp,_tmpTimeFormatted,_tmpDurationSeconds,_tmpDirection,_tmpCalls1Min,_tmpCalls5Min,_tmpCalls1Hour,_tmpAverageDurationSeconds,_tmpShortCallRatio,_tmpRepeatCallRatio,_tmpNightCallRatio,_tmpBurstScore,_tmpRiskScore,_tmpRiskLevel,_tmpPredictionReasons,_tmpModelVersion,_tmpDecisionSource,_tmpMatchedContextOrg,_tmpIsTrusted,_tmpFinalDecision,_tmpUserFeedback);
            _result.add(_item);
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

  @Override
  public Object getCallById(final String id,
      final Continuation<? super CallEventEntity> $completion) {
    final String _sql = "SELECT * FROM call_events WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CallEventEntity>() {
      @Override
      @Nullable
      public CallEventEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfPhoneNumberHash = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumberHash");
          final int _cursorIndexOfCallerName = CursorUtil.getColumnIndexOrThrow(_cursor, "callerName");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimeFormatted = CursorUtil.getColumnIndexOrThrow(_cursor, "timeFormatted");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "direction");
          final int _cursorIndexOfCalls1Min = CursorUtil.getColumnIndexOrThrow(_cursor, "calls1Min");
          final int _cursorIndexOfCalls5Min = CursorUtil.getColumnIndexOrThrow(_cursor, "calls5Min");
          final int _cursorIndexOfCalls1Hour = CursorUtil.getColumnIndexOrThrow(_cursor, "calls1Hour");
          final int _cursorIndexOfAverageDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "averageDurationSeconds");
          final int _cursorIndexOfShortCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "shortCallRatio");
          final int _cursorIndexOfRepeatCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatCallRatio");
          final int _cursorIndexOfNightCallRatio = CursorUtil.getColumnIndexOrThrow(_cursor, "nightCallRatio");
          final int _cursorIndexOfBurstScore = CursorUtil.getColumnIndexOrThrow(_cursor, "burstScore");
          final int _cursorIndexOfRiskScore = CursorUtil.getColumnIndexOrThrow(_cursor, "riskScore");
          final int _cursorIndexOfRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "riskLevel");
          final int _cursorIndexOfPredictionReasons = CursorUtil.getColumnIndexOrThrow(_cursor, "predictionReasons");
          final int _cursorIndexOfModelVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "modelVersion");
          final int _cursorIndexOfDecisionSource = CursorUtil.getColumnIndexOrThrow(_cursor, "decisionSource");
          final int _cursorIndexOfMatchedContextOrg = CursorUtil.getColumnIndexOrThrow(_cursor, "matchedContextOrg");
          final int _cursorIndexOfIsTrusted = CursorUtil.getColumnIndexOrThrow(_cursor, "isTrusted");
          final int _cursorIndexOfFinalDecision = CursorUtil.getColumnIndexOrThrow(_cursor, "finalDecision");
          final int _cursorIndexOfUserFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "userFeedback");
          final CallEventEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpPhoneNumberHash;
            _tmpPhoneNumberHash = _cursor.getString(_cursorIndexOfPhoneNumberHash);
            final String _tmpCallerName;
            if (_cursor.isNull(_cursorIndexOfCallerName)) {
              _tmpCallerName = null;
            } else {
              _tmpCallerName = _cursor.getString(_cursorIndexOfCallerName);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpTimeFormatted;
            _tmpTimeFormatted = _cursor.getString(_cursorIndexOfTimeFormatted);
            final int _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getInt(_cursorIndexOfDurationSeconds);
            final CallDirection _tmpDirection;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfDirection);
            _tmpDirection = __converters.toCallDirection(_tmp);
            final int _tmpCalls1Min;
            _tmpCalls1Min = _cursor.getInt(_cursorIndexOfCalls1Min);
            final int _tmpCalls5Min;
            _tmpCalls5Min = _cursor.getInt(_cursorIndexOfCalls5Min);
            final int _tmpCalls1Hour;
            _tmpCalls1Hour = _cursor.getInt(_cursorIndexOfCalls1Hour);
            final float _tmpAverageDurationSeconds;
            _tmpAverageDurationSeconds = _cursor.getFloat(_cursorIndexOfAverageDurationSeconds);
            final float _tmpShortCallRatio;
            _tmpShortCallRatio = _cursor.getFloat(_cursorIndexOfShortCallRatio);
            final float _tmpRepeatCallRatio;
            _tmpRepeatCallRatio = _cursor.getFloat(_cursorIndexOfRepeatCallRatio);
            final float _tmpNightCallRatio;
            _tmpNightCallRatio = _cursor.getFloat(_cursorIndexOfNightCallRatio);
            final float _tmpBurstScore;
            _tmpBurstScore = _cursor.getFloat(_cursorIndexOfBurstScore);
            final int _tmpRiskScore;
            _tmpRiskScore = _cursor.getInt(_cursorIndexOfRiskScore);
            final RiskLevel _tmpRiskLevel;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfRiskLevel);
            _tmpRiskLevel = __converters.toRiskLevel(_tmp_1);
            final List<String> _tmpPredictionReasons;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfPredictionReasons);
            _tmpPredictionReasons = __converters.toStringList(_tmp_2);
            final String _tmpModelVersion;
            _tmpModelVersion = _cursor.getString(_cursorIndexOfModelVersion);
            final String _tmpDecisionSource;
            _tmpDecisionSource = _cursor.getString(_cursorIndexOfDecisionSource);
            final String _tmpMatchedContextOrg;
            if (_cursor.isNull(_cursorIndexOfMatchedContextOrg)) {
              _tmpMatchedContextOrg = null;
            } else {
              _tmpMatchedContextOrg = _cursor.getString(_cursorIndexOfMatchedContextOrg);
            }
            final boolean _tmpIsTrusted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsTrusted);
            _tmpIsTrusted = _tmp_3 != 0;
            final CallDecision _tmpFinalDecision;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfFinalDecision);
            _tmpFinalDecision = __converters.toCallDecision(_tmp_4);
            final UserFeedbackType _tmpUserFeedback;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfUserFeedback)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfUserFeedback);
            }
            _tmpUserFeedback = __converters.toUserFeedbackType(_tmp_5);
            _result = new CallEventEntity(_tmpId,_tmpPhoneNumber,_tmpPhoneNumberHash,_tmpCallerName,_tmpTimestamp,_tmpTimeFormatted,_tmpDurationSeconds,_tmpDirection,_tmpCalls1Min,_tmpCalls5Min,_tmpCalls1Hour,_tmpAverageDurationSeconds,_tmpShortCallRatio,_tmpRepeatCallRatio,_tmpNightCallRatio,_tmpBurstScore,_tmpRiskScore,_tmpRiskLevel,_tmpPredictionReasons,_tmpModelVersion,_tmpDecisionSource,_tmpMatchedContextOrg,_tmpIsTrusted,_tmpFinalDecision,_tmpUserFeedback);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
