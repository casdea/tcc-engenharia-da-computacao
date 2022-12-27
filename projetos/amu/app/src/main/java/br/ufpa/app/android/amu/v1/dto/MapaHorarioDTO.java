package br.ufpa.app.android.amu.v1.dto;

import java.util.Date;

public class MapaHorarioDTO
{
	private int sequencia;

	private Date horaPrescrita;

	private Date horaAdministrada;

	public MapaHorarioDTO(int sequencia, Date horaPrescrita, Date horaAdministrada)
	{
		super();
		this.sequencia = sequencia;
		this.horaPrescrita = horaPrescrita;
		this.horaAdministrada = horaAdministrada;
	}

	public int getSequencia()
	{
		return sequencia;
	}

	public void setSequencia(int sequencia)
	{
		this.sequencia = sequencia;
	}

	public Date getHoraPrescrita()
	{
		return horaPrescrita;
	}

	public void setHoraPrescrita(Date horaPrescrita)
	{
		this.horaPrescrita = horaPrescrita;
	}

	public Date getHoraAdministrada()
	{
		return horaAdministrada;
	}

	public void setHoraAdministrada(Date horaAdministrada)
	{
		this.horaAdministrada = horaAdministrada;
	}
}
