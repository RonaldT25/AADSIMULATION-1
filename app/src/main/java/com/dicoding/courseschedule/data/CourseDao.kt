package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {
    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>
    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>
    @Query("SELECT * FROM Course WHERE id IN (:id)")
    fun getCourse(id: Int): LiveData<Course>
    @Query("SELECT * FROM Course WHERE day IN (:day)")
    fun getTodaySchedule(day: Int): List<Course>
    @Insert
    fun insert(course: Course)
    @Delete
    fun delete(course: Course)
}