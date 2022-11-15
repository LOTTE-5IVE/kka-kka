import { useState } from "react";

export default function ValidDuration({
  setStartDate,
  setEndDate,
  setSDateValid,
  setEDateValid,
}) {
  const today = new Date().toISOString().substring(0, 10);
  const [tempSdate, settempSdate] = useState();
  const [tempEdate, settempEdate] = useState();

  return (
    <div className="dateWrapper" style={{ display: "flex" }}>
      <div className="date">
        <input
          id="oname"
          className="inputTypeText"
          type="date"
          defaultValue={tempSdate}
          onChange={(e) => {
            if (e.target.value >= tempEdate) {
              alert("날짜를 다시 설정해주세요.");
              setStartDate("");
              settempSdate("");
              setSDateValid(false);
            } else {
              setStartDate(e.target.value + "");
              settempSdate(e.target.value + "");
              setSDateValid(true);
            }
          }}
        />{" "}
        ~{" "}
        <input
          id="oname"
          className="inputTypeText"
          type="date"
          defaultValue={tempEdate}
          onChange={(e) => {
            if (e.target.value <= today || e.target.value <= tempSdate) {
              alert("날짜를 다시 설정해주세요.");
              setEndDate("");
              settempEdate("");
              setEDateValid(false);
            } else {
              setEndDate(e.target.value + "");
              settempEdate(e.target.value + "");
              setEDateValid(true);
            }
          }}
        />
      </div>
    </div>
  );
}
