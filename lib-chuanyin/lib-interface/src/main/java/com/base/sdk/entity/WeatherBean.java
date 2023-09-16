package com.base.sdk.entity;

import java.util.List;

public class WeatherBean {

    /**
     * location : {"country":"US","city":"Ashburn","woeid":4744870,"timezone_id":"America/New_York","region":"Virginia","lat":39.0469,"long":-77.4903}
     * current_observation : {"atmosphere":{"rising":0,"visibility":0,"humidity":0,"pressure":0},"condition":{"code":27,"temperature":77,"text":"Clouds"},"astronomy":{"sunrise":0,"sunset":0},"pubDate":1686535550,"wind":{"chill":0,"speed":0,"direction":0}}
     * forecasts : [{"date":1686502800,"high":86,"code":27,"low":58,"text":"Clouds","day":"Sun"},{"date":1686589200,"high":76,"code":11,"low":59,"text":"Rain","day":"Mon"},{"date":1686675600,"high":79,"code":30,"low":52,"text":"Clouds","day":"Tue"},{"date":1686762000,"high":75,"code":9,"low":56,"text":"Rain","day":"Wed"},{"date":1686848400,"high":78,"code":28,"low":53,"text":"Clouds","day":"Thu"}]
     */

    private long pubDate;
    private LocationBean location;
    private List<ForecastsBean> forecasts;

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public long getPubDate() {
        return pubDate;
    }

    public void setPubDate(long pubDate) {
        this.pubDate = pubDate;
    }

    public List<ForecastsBean> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsBean> forecasts) {
        this.forecasts = forecasts;
    }

    public static class LocationBean  {
        /**
         * country : US
         * city : Ashburn
         * woeid : 4744870
         * timezone_id : America/New_York
         * region : Virginia
         * lat : 39.0469
         * long : -77.4903
         */

        private String country;
        private String city;
        private int woeid;
        private String timezone_id;
        private String region;
        private double latitude;
        private double longitude;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getWoeid() {
            return woeid;
        }

        public void setWoeid(int woeid) {
            this.woeid = woeid;
        }

        public String getTimezone_id() {
            return timezone_id;
        }

        public void setTimezone_id(String timezone_id) {
            this.timezone_id = timezone_id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "LocationBean{" +
                    "country='" + country + '\'' +
                    ", city='" + city + '\'' +
                    ", woeid=" + woeid +
                    ", timezone_id='" + timezone_id + '\'' +
                    ", region='" + region + '\'' +
                    ", lat=" + latitude +
                    ", longX=" + longitude +
                    '}';
        }
    }

    public static class ForecastsBean  {
        /**
         * date : 1686502800
         * high : 86
         * code : 27
         * low : 58
         * text : Clouds
         * day : Sun
         */

        private long date;
        private int high;
        private int code;
        private int low;
        private String text;
        private String day;

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public int getHigh() {
            return high;
        }

        public void setHigh(int high) {
            this.high = high;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getLow() {
            return low;
        }

        public void setLow(int low) {
            this.low = low;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        @Override
        public String toString() {
            return "ForecastsBean{" +
                    "date=" + date +
                    ", high=" + high +
                    ", code=" + code +
                    ", low=" + low +
                    ", text='" + text + '\'' +
                    ", day='" + day + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "location=" + location +
                ", forecasts=" + forecasts +
                '}';
    }
}
