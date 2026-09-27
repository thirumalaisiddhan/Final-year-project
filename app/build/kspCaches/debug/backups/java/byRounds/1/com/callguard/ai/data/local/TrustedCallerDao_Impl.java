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
import com.callguard.ai.data.model.TrustedCategory;
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
public final class TrustedCallerDao_Impl implements TrustedCallerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrustedCallerEntity> __insertionAdapterOfTrustedCallerEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<TrustedCallerEntity> __updateAdapterOfTrustedCallerEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public TrustedCallerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrustedCallerEntity = new EntityInsertionAdapter<TrustedCallerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trusted_callers` (`id`,`name`,`phoneNumberOrIdentifier`,`category`,`reasonForTrust`,`approvalDuration`,`notes`,`isEnabled`,`isOrganization`,`approvalStatus`,`addedDate`,`expiryDate`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrustedCallerEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumberOrIdentifier());
        final String _tmp = __converters.fromTrustedCategory(entity.getCategory());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getReasonForTrust());
        statement.bindString(6, entity.getApprovalDuration());
        statement.bindString(7, entity.getNotes());
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        final int _tmp_2 = entity.isOrganization() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindString(10, entity.getApprovalStatus());
        statement.bindString(11, entity.getAddedDate());
        if (entity.getExpiryDate() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getExpiryDate());
        }
      }
    };
    this.__updateAdapterOfTrustedCallerEntity = new EntityDeletionOrUpdateAdapter<TrustedCallerEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trusted_callers` SET `id` = ?,`name` = ?,`phoneNumberOrIdentifier` = ?,`category` = ?,`reasonForTrust` = ?,`approvalDuration` = ?,`notes` = ?,`isEnabled` = ?,`isOrganization` = ?,`approvalStatus` = ?,`addedDate` = ?,`expiryDate` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TrustedCallerEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumberOrIdentifier());
        final String _tmp = __converters.fromTrustedCategory(entity.getCategory());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getReasonForTrust());
        statement.bindString(6, entity.getApprovalDuration());
        statement.bindString(7, entity.getNotes());
        final int _tmp_1 = entity.isEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        final int _tmp_2 = entity.isOrganization() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        statement.bindString(10, entity.getApprovalStatus());
        statement.bindString(11, entity.getAddedDate());
        if (entity.getExpiryDate() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getExpiryDate());
        }
        statement.bindString(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trusted_callers WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TrustedCallerEntity trusted,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrustedCallerEntity.insert(trusted);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<TrustedCallerEntity> list,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTrustedCallerEntity.insert(list);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TrustedCallerEntity trusted,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTrustedCallerEntity.handle(trusted);
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
  public Flow<List<TrustedCallerEntity>> getAllTrusted() {
    final String _sql = "SELECT * FROM trusted_callers ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trusted_callers"}, new Callable<List<TrustedCallerEntity>>() {
      @Override
      @NonNull
      public List<TrustedCallerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumberOrIdentifier = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumberOrIdentifier");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfReasonForTrust = CursorUtil.getColumnIndexOrThrow(_cursor, "reasonForTrust");
          final int _cursorIndexOfApprovalDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "approvalDuration");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfIsOrganization = CursorUtil.getColumnIndexOrThrow(_cursor, "isOrganization");
          final int _cursorIndexOfApprovalStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "approvalStatus");
          final int _cursorIndexOfAddedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "addedDate");
          final int _cursorIndexOfExpiryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "expiryDate");
          final List<TrustedCallerEntity> _result = new ArrayList<TrustedCallerEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrustedCallerEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumberOrIdentifier;
            _tmpPhoneNumberOrIdentifier = _cursor.getString(_cursorIndexOfPhoneNumberOrIdentifier);
            final TrustedCategory _tmpCategory;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCategory);
            _tmpCategory = __converters.toTrustedCategory(_tmp);
            final String _tmpReasonForTrust;
            _tmpReasonForTrust = _cursor.getString(_cursorIndexOfReasonForTrust);
            final String _tmpApprovalDuration;
            _tmpApprovalDuration = _cursor.getString(_cursorIndexOfApprovalDuration);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final boolean _tmpIsEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp_1 != 0;
            final boolean _tmpIsOrganization;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsOrganization);
            _tmpIsOrganization = _tmp_2 != 0;
            final String _tmpApprovalStatus;
            _tmpApprovalStatus = _cursor.getString(_cursorIndexOfApprovalStatus);
            final String _tmpAddedDate;
            _tmpAddedDate = _cursor.getString(_cursorIndexOfAddedDate);
            final String _tmpExpiryDate;
            if (_cursor.isNull(_cursorIndexOfExpiryDate)) {
              _tmpExpiryDate = null;
            } else {
              _tmpExpiryDate = _cursor.getString(_cursorIndexOfExpiryDate);
            }
            _item = new TrustedCallerEntity(_tmpId,_tmpName,_tmpPhoneNumberOrIdentifier,_tmpCategory,_tmpReasonForTrust,_tmpApprovalDuration,_tmpNotes,_tmpIsEnabled,_tmpIsOrganization,_tmpApprovalStatus,_tmpAddedDate,_tmpExpiryDate);
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
