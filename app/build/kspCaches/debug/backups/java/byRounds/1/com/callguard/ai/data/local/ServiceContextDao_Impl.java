package com.callguard.ai.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class ServiceContextDao_Impl implements ServiceContextDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ServiceContextEntity> __insertionAdapterOfServiceContextEntity;

  private final EntityDeletionOrUpdateAdapter<ServiceContextEntity> __updateAdapterOfServiceContextEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public ServiceContextDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfServiceContextEntity = new EntityInsertionAdapter<ServiceContextEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `service_context` (`id`,`organizationName`,`category`,`status`,`expectedDate`,`deliveryCallsAllowed`,`isEnabled`,`notes`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ServiceContextEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getOrganizationName());
        statement.bindString(3, entity.getCategory());
        statement.bindString(4, entity.getStatus());
        statement.bindString(5, entity.getExpectedDate());
        final int _tmp = entity.getDeliveryCallsAllowed() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindString(8, entity.getNotes());
      }
    };
    this.__updateAdapterOfServiceContextEntity = new EntityDeletionOrUpdateAdapter<ServiceContextEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `service_context` SET `id` = ?,`organizationName` = ?,`category` = ?,`status` = ?,`expectedDate` = ?,`deliveryCallsAllowed` = ?,`isEnabled` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ServiceContextEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getOrganizationName());
        statement.bindString(3, entity.getCategory());
        statement.bindString(4, entity.getStatus());
        statement.bindString(5, entity.getExpectedDate());
        final int _tmp = entity.getDeliveryCallsAllowed() ? 1 : 0;
        statement.bindLong(6, _tmp);
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindString(8, entity.getNotes());
        statement.bindString(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM service_context WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ServiceContextEntity context,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfServiceContextEntity.insert(context);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<ServiceContextEntity> list,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfServiceContextEntity.insert(list);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ServiceContextEntity context,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfServiceContextEntity.handle(context);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ServiceContextEntity>> getAllContexts() {
    final String _sql = "SELECT * FROM service_context ORDER BY organizationName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"service_context"}, new Callable<List<ServiceContextEntity>>() {
      @Override
      @NonNull
      public List<ServiceContextEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOrganizationName = CursorUtil.getColumnIndexOrThrow(_cursor, "organizationName");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfExpectedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expectedDate");
          final int _cursorIndexOfDeliveryCallsAllowed = CursorUtil.getColumnIndexOrThrow(_cursor, "deliveryCallsAllowed");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<ServiceContextEntity> _result = new ArrayList<ServiceContextEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ServiceContextEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOrganizationName;
            _tmpOrganizationName = _cursor.getString(_cursorIndexOfOrganizationName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpExpectedDate;
            _tmpExpectedDate = _cursor.getString(_cursorIndexOfExpectedDate);
            final boolean _tmpDeliveryCallsAllowed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDeliveryCallsAllowed);
            _tmpDeliveryCallsAllowed = _tmp != 0;
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new ServiceContextEntity(_tmpId,_tmpOrganizationName,_tmpCategory,_tmpStatus,_tmpExpectedDate,_tmpDeliveryCallsAllowed,_tmpIsEnabled,_tmpNotes);
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
