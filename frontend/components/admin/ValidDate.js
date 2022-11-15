import { useEffect, useState } from "react";

export default function ValidDate({
  setStartDate,
  setEndDate,
  setSDateValid,
  setEDateValid,
}) {
  let curr = new Date();
  curr.setDate(curr.getDate());
  const date = curr.toISOString().substring(0, 10);
  const [duration, setDuration] = useState(0);

  useEffect(() => {
    setStartDate(date);
    setSDateValid(true);
  }, [duration]);

  return (
    <div className="dateWrapper" style={{ display: "flex" }}>
      <div>
        발급일로부터{" "}
        <input
          type="text"
          size="1"
          defaultValue={duration}
          onChange={(e) => {
            if (e.target.value > 0) {
              let now = new Date();
              let dur = new Date(
                now.setDate(now.getDate() + Number(e.target.value)),
              );
              setDuration(e.target.value);
              setEndDate(dur.toISOString().substring(0, 10));
              setEDateValid(true);
            } else {
              alert("양수만 입력 가능합니다.");
              setEDateValid(false);
            }
          }}
        />{" "}
        일 유효
      </div>
    </div>
  );
}
