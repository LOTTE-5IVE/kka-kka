export default function ValidDuration({
  startDate,
  setStartDate,
  endDate,
  setEndDate,
}) {
  return (
    <div className="dateWrapper" style={{ display: "flex" }}>
      <div className="date">
        <input
          id="oname"
          className="inputTypeText"
          type="date"
          defaultValue={startDate}
          onChange={(e) => {
            setStartDate(e.target.value + "");
          }}
        />{" "}
        <input
          id="oname"
          className="inputTypeText"
          type="date"
          defaultValue={endDate}
          onChange={(e) => {
            setEndDate(e.target.value + "");
          }}
        />
      </div>
    </div>
  );
}
