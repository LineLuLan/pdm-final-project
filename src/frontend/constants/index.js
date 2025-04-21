// src/constants/index.js

export const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

export const NAV_ITEMS = [
  { name: "Dashboard", path: "/", icon: "dashboard" },
  { name: "Donors", path: "/donors", icon: "users" },
  { name: "Patients", path: "/patients", icon: "user" },
  { name: "Doctors", path: "/doctors", icon: "stethoscope" },
  { name: "Blood Banks", path: "/bloodbanks", icon: "bank" },
  { name: "Blood Stocks", path: "/bloodstocks", icon: "drop" },
  { name: "Donate Times", path: "/donatetimes", icon: "gift" },
  { name: "Request Times", path: "/requesttimes", icon: "clipboard" },
];
