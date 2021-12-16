package com.spun.util.persistence;

import java.sql.SQLException;
import java.sql.Statement;

import com.spun.util.database.DatabaseObject;

public class DatabaseObjectDeletor<T extends DatabaseObject> implements Deletor<T>
{
  private Statement stmt;
  public DatabaseObjectDeletor(Statement stmt)
  {
    this.stmt = stmt;
  }
  public void delete(T delete) throws SavingException
  {
    delete.deleteFromDatabase(stmt);
  }
}
