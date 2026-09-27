package com.callguard.ai.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.callguard.ai.data.model.UserFeedbackType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
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
public final class UserFeedbackDao_Impl implements UserFeedbackDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserFeedbackEntity> __insertionAdapterOfUserFeedbackEntity;

  private final Converters __converters = new Converters();

  public UserFeedbackDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserFeedbackEntity = new EntityInsertionAdapter<UserFeedbackEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_feedback` (`id`,`callEventId`,`phoneNumber`,`feedback`,`trustedAfterFeedback`,`timestamp`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserFeedbackEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getCallEventId());
        statement.bindString(3, entity.getPhoneNumber());
        final String _tmp = __converters.fromUserFeedbackType(entity.getFeedback());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final int _tmp_1 = entity.getTrustedAfterFeedback() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        statement.bindLong(6, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insertFeedback(final UserFeedbackEntity feedback,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserFeedbackEntity.insert(feedback);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<UserFeedbackEntity>> getAllFeedback() {
    final String _sql = "SELECT * FROM user_feedback ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_feedback"}, new Callable<List<UserFeedbackEntity>>() {
      @Override
      @NonNull
      public List<UserFeedbackEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCallEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "callEventId");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "feedback");
          final int _cursorIndexOfTrustedAfterFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "trustedAfterFeedback");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<UserFeedbackEntity> _result = new ArrayList<UserFeedbackEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final UserFeedbackEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpCallEventId;
            _tmpCallEventId = _cursor.getString(_cursorIndexOfCallEventId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final UserFeedbackType _tmpFeedback;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfFeedback)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfFeedback);
            }
            final UserFeedbackType _tmp_1 = __converters.toUserFeedbackType(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callguard.ai.data.model.UserFeedbackType', but it was NULL.");
            } else {
              _tmpFeedback = _tmp_1;
            }
            final boolean _tmpTrustedAfterFeedback;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfTrustedAfterFeedback);
            _tmpTrustedAfterFeedback = _tmp_2 != 0;
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new UserFeedbackEntity(_tmpId,_tmpCallEventId,_tmpPhoneNumber,_tmpFeedback,_tmpTrustedAfterFeedback,_tmpTimestamp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
