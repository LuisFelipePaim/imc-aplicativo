package com.example.calculadoraimc.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
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
public final class IMCDao_Impl implements IMCDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<IMCResultEntity> __insertionAdapterOfIMCResultEntity;

  private final EntityDeletionOrUpdateAdapter<IMCResultEntity> __deletionAdapterOfIMCResultEntity;

  public IMCDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIMCResultEntity = new EntityInsertionAdapter<IMCResultEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `imc_history` (`id`,`date`,`weight`,`height`,`imc`,`classification`,`tmb`,`tdee`,`bodyFat`,`waist`,`neck`,`hip`,`idealWeightMin`,`idealWeightMax`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IMCResultEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindDouble(3, entity.getWeight());
        statement.bindDouble(4, entity.getHeight());
        statement.bindDouble(5, entity.getImc());
        statement.bindString(6, entity.getClassification());
        statement.bindDouble(7, entity.getTmb());
        statement.bindDouble(8, entity.getTdee());
        statement.bindDouble(9, entity.getBodyFat());
        statement.bindDouble(10, entity.getWaist());
        statement.bindDouble(11, entity.getNeck());
        statement.bindDouble(12, entity.getHip());
        statement.bindDouble(13, entity.getIdealWeightMin());
        statement.bindDouble(14, entity.getIdealWeightMax());
      }
    };
    this.__deletionAdapterOfIMCResultEntity = new EntityDeletionOrUpdateAdapter<IMCResultEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `imc_history` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IMCResultEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final IMCResultEntity imcResult,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfIMCResultEntity.insert(imcResult);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final IMCResultEntity imcResult,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfIMCResultEntity.handle(imcResult);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<IMCResultEntity>> getAllHistory() {
    final String _sql = "SELECT * FROM imc_history ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"imc_history"}, new Callable<List<IMCResultEntity>>() {
      @Override
      @NonNull
      public List<IMCResultEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfImc = CursorUtil.getColumnIndexOrThrow(_cursor, "imc");
          final int _cursorIndexOfClassification = CursorUtil.getColumnIndexOrThrow(_cursor, "classification");
          final int _cursorIndexOfTmb = CursorUtil.getColumnIndexOrThrow(_cursor, "tmb");
          final int _cursorIndexOfTdee = CursorUtil.getColumnIndexOrThrow(_cursor, "tdee");
          final int _cursorIndexOfBodyFat = CursorUtil.getColumnIndexOrThrow(_cursor, "bodyFat");
          final int _cursorIndexOfWaist = CursorUtil.getColumnIndexOrThrow(_cursor, "waist");
          final int _cursorIndexOfNeck = CursorUtil.getColumnIndexOrThrow(_cursor, "neck");
          final int _cursorIndexOfHip = CursorUtil.getColumnIndexOrThrow(_cursor, "hip");
          final int _cursorIndexOfIdealWeightMin = CursorUtil.getColumnIndexOrThrow(_cursor, "idealWeightMin");
          final int _cursorIndexOfIdealWeightMax = CursorUtil.getColumnIndexOrThrow(_cursor, "idealWeightMax");
          final List<IMCResultEntity> _result = new ArrayList<IMCResultEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IMCResultEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            final double _tmpImc;
            _tmpImc = _cursor.getDouble(_cursorIndexOfImc);
            final String _tmpClassification;
            _tmpClassification = _cursor.getString(_cursorIndexOfClassification);
            final double _tmpTmb;
            _tmpTmb = _cursor.getDouble(_cursorIndexOfTmb);
            final double _tmpTdee;
            _tmpTdee = _cursor.getDouble(_cursorIndexOfTdee);
            final double _tmpBodyFat;
            _tmpBodyFat = _cursor.getDouble(_cursorIndexOfBodyFat);
            final double _tmpWaist;
            _tmpWaist = _cursor.getDouble(_cursorIndexOfWaist);
            final double _tmpNeck;
            _tmpNeck = _cursor.getDouble(_cursorIndexOfNeck);
            final double _tmpHip;
            _tmpHip = _cursor.getDouble(_cursorIndexOfHip);
            final double _tmpIdealWeightMin;
            _tmpIdealWeightMin = _cursor.getDouble(_cursorIndexOfIdealWeightMin);
            final double _tmpIdealWeightMax;
            _tmpIdealWeightMax = _cursor.getDouble(_cursorIndexOfIdealWeightMax);
            _item = new IMCResultEntity(_tmpId,_tmpDate,_tmpWeight,_tmpHeight,_tmpImc,_tmpClassification,_tmpTmb,_tmpTdee,_tmpBodyFat,_tmpWaist,_tmpNeck,_tmpHip,_tmpIdealWeightMin,_tmpIdealWeightMax);
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
